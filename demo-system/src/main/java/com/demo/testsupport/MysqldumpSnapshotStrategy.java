package com.demo.testsupport;

import com.qualitest.testsupport.exception.TestSupportException;
import com.qualitest.testsupport.model.SnapshotCommand;
import com.qualitest.testsupport.model.SnapshotResult;
import com.qualitest.testsupport.spi.SnapshotStrategy;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demo 被测库快照策略：通过本机 mysqldump / mysql 客户端完成备份与还原。
 * <p>
 * snapshot：生成 {@code {SNAPSHOT_DIR}/{snapshotId}.sql}，将 snapshotId 返回给调用方（质衡 Run 侧维护快照栈）。
 * restore：调用方传入 snapshotId，按约定路径定位 dump 文件并导入（无需在被测库建元数据表）。
 * 请求未带 tables 时导出整库；带了 tables 时仅导出列出的表。
 * <p>
 * 客户端路径、快照目录、超时等见类内常量，按部署机器修改，不写入 application.yml。
 */
@Slf4j
@Component
public class MysqldumpSnapshotStrategy implements SnapshotStrategy {

    // -------------------------------------------------------------------------
    // 本机环境常量（接入方按部署机器修改）
    // -------------------------------------------------------------------------

    /** 本机 mysqldump、mysql 可执行文件所在目录 */
    private static final String MYSQL_BIN_DIR = "C:/Program Files/MySQL/MySQL Server 8.0/bin";

    /** 快照 SQL 文件落盘目录；文件名规则为 {snapshotId}.sql */
    private static final Path SNAPSHOT_DIR = Path.of("D:/demo/uploadPath/test-support-snapshots");

    /** mysqldump / mysql 子进程最长等待时间（毫秒）；整库体积大时可酌情调大 */
    private static final long CLI_TIMEOUT_MS = 300_000L;

    /** snapshotId 格式：snap- + UUID，用于防止 restore 时路径穿越 */
    private static final Pattern SNAPSHOT_ID_PATTERN = Pattern.compile(
            "^snap-[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$"
    );

    /** 从 spring 数据源 JDBC URL 解析 host / port / database */
    private static final Pattern JDBC_MYSQL = Pattern.compile("jdbc:mysql://([^/:?]+)(?::(\\d+))?/([^?]+)");

    /** 表名校验，仅允许字母数字下划线，防止拼接进命令行时注入 */
    private static final Pattern TABLE_NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]+$");

    /** 当前应用主库 JDBC URL，用于解析 mysqldump 连接参数 */
    @Value("${spring.datasource.druid.master.url}")
    private String jdbcUrl;

    @Value("${spring.datasource.druid.master.username}")
    private String dbUsername;

    @Value("${spring.datasource.druid.master.password}")
    private String dbPassword;

    /** 启动时创建快照目录 */
    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(SNAPSHOT_DIR);
    }

    /**
     * 创建快照：mysqldump 写出 {snapshotId}.sql，将 snapshotId 返回给调用方保存。
     *
     * @return snapshotId、scope（database / tables）、status=ready
     */
    @Override
    public SnapshotResult snapshot(SnapshotCommand cmd) {
        List<String> tables = resolveTables(cmd);
        boolean fullDatabase = tables.isEmpty();
        String snapshotId = "snap-" + UUID.randomUUID();
        Instant createdAt = Instant.now();
        Path dumpFile = resolveDumpFile(snapshotId);

        log.info("mysqldump snapshot: id={}, label={}, fullDatabase={}, tables={}, file={}",
                snapshotId, cmd.getLabel(), fullDatabase, tables, dumpFile);

        try {
            dump(connectionInfo(), resolveExecutable("mysqldump"), dumpFile, tables);
        } catch (TestSupportException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TestSupportException("INTERNAL_ERROR", "snapshot failed: " + ex.getMessage(), ex);
        }

        String scope = fullDatabase ? "database" : (StringUtils.hasText(cmd.getScope()) ? cmd.getScope() : "tables");
        return SnapshotResult.builder()
                .snapshotId(snapshotId)
                .createdAt(createdAt)
                .scope(scope)
                .status("ready")
                .build();
    }

    /**
     * 按 snapshotId 还原：根据约定路径找到 dump 文件，mysql 导入。
     * snapshotId 由调用方（质衡）在 Run 快照栈中保存并回传。
     */
    @Override
    public void restore(String snapshotId) {
        validateSnapshotId(snapshotId);
        Path dumpFile = resolveDumpFile(snapshotId);
        if (!Files.isRegularFile(dumpFile)) {
            throw new TestSupportException("NOT_FOUND", "dump file not found: " + dumpFile);
        }

        log.info("mysql restore: id={}, file={}", snapshotId, dumpFile);

        try {
            restoreDump(connectionInfo(), resolveExecutable("mysql"), dumpFile);
        } catch (TestSupportException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new TestSupportException("INTERNAL_ERROR", "restore failed: " + ex.getMessage(), ex);
        }
    }

    /**
     * 由 snapshotId 推导 dump 文件路径：{SNAPSHOT_DIR}/{snapshotId}.sql
     */
    private Path resolveDumpFile(String snapshotId) {
        validateSnapshotId(snapshotId);
        Path file = SNAPSHOT_DIR.resolve(snapshotId + ".sql").normalize();
        if (!file.startsWith(SNAPSHOT_DIR.normalize())) {
            throw new TestSupportException("BAD_REQUEST", "invalid snapshotId");
        }
        return file;
    }

    /** 仅接受本策略生成的 snapshotId 格式 */
    private static void validateSnapshotId(String snapshotId) {
        if (!StringUtils.hasText(snapshotId) || !SNAPSHOT_ID_PATTERN.matcher(snapshotId).matches()) {
            throw new TestSupportException("BAD_REQUEST", "invalid snapshotId");
        }
    }

    /**
     * 解析本次要导出的表名列表。
     * 请求 tables 为空 → 返回空列表，表示整库；否则返回校验后的表名。
     */
    private List<String> resolveTables(SnapshotCommand cmd) {
        if (CollectionUtils.isEmpty(cmd.getTables())) {
            return List.of();
        }
        return cmd.getTables().stream().map(String::trim).filter(StringUtils::hasText).peek(this::validateTableName).toList();
    }

    /** 从 Spring 数据源配置组装 mysqldump / mysql 连接信息 */
    private ConnectionInfo connectionInfo() {
        Matcher matcher = JDBC_MYSQL.matcher(jdbcUrl);
        if (!matcher.find()) {
            throw new TestSupportException("INTERNAL_ERROR", "unsupported jdbc url: " + jdbcUrl);
        }
        int port = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 3306;
        return new ConnectionInfo(matcher.group(1), port, matcher.group(3), dbUsername, dbPassword);
    }

    /**
     * 执行 mysqldump。
     * tables 为空：mysqldump … database（整库）；
     * tables 非空：mysqldump … database table1 table2 …（表级）。
     */
    private static void dump(ConnectionInfo conn, Path mysqldump, Path dumpFile, List<String> tables)
            throws IOException, InterruptedException {
        Path cnf = writeClientCnf(conn, dumpFile.getParent());
        try {
            List<String> command = new ArrayList<>();
            command.add(mysqldump.toString());
            command.add("--defaults-extra-file=" + cnf.toAbsolutePath());
            command.add("--single-transaction");
            command.add("--set-charset");
            command.add("--default-character-set=utf8mb4");
            command.add("--result-file=" + dumpFile.toAbsolutePath());
            command.add(conn.getDatabase());
            if (!tables.isEmpty()) {
                command.addAll(tables);
            }
            runProcess(command, CLI_TIMEOUT_MS, "mysqldump");
            if (!Files.isRegularFile(dumpFile) || Files.size(dumpFile) <= 0) {
                throw new TestSupportException("INTERNAL_ERROR", "mysqldump produced empty file");
            }
        } finally {
            Files.deleteIfExists(cnf);
        }
    }

    /**
     * 执行 mysql 客户端，将 dump 文件内容导入目标库。
     * 标准输入重定向为 SQL 文件，等价于 mysql db &lt; dump.sql。
     */
    private static void restoreDump(ConnectionInfo conn, Path mysql, Path dumpFile)
            throws IOException, InterruptedException {
        Path cnf = writeClientCnf(conn, dumpFile.getParent());
        try {
            List<String> command = List.of(
                    mysql.toString(),
                    "--defaults-extra-file=" + cnf.toAbsolutePath(),
                    conn.getDatabase()
            );
            runProcess(command, CLI_TIMEOUT_MS, "mysql", dumpFile);
        } finally {
            Files.deleteIfExists(cnf);
        }
    }

    /**
     * 解析 mysqldump 或 mysql 可执行路径。
     * 优先 MYSQL_BIN_DIR 下查找；找不到则退回 PATH 中的命令名。
     */
    private static Path resolveExecutable(String name) {
        String fileName = isWindows() ? name + ".exe" : name;
        Path candidate = Path.of(MYSQL_BIN_DIR, fileName);
        if (Files.isExecutable(candidate)) {
            return candidate;
        }
        return Path.of(fileName);
    }

    /**
     * 写入临时 [client] 配置文件，供 --defaults-extra-file 使用。
     * 避免在命令行参数中暴露密码；用后由调用方删除。
     */
    private static Path writeClientCnf(ConnectionInfo conn, Path dir) throws IOException {
        Files.createDirectories(dir);
        Path cnf = Files.createTempFile(dir, "_qt_db_", ".cnf");
        try (BufferedWriter writer = Files.newBufferedWriter(cnf, StandardCharsets.UTF_8)) {
            writer.write("[client]");
            writer.newLine();
            writer.write("host=" + conn.getHost());
            writer.newLine();
            writer.write("port=" + conn.getPort());
            writer.newLine();
            writer.write("user=" + conn.getUsername());
            writer.newLine();
            writer.write("password=" + conn.getPassword());
            writer.newLine();
        }
        return cnf;
    }

    /** 启动子进程并等待结束；超时时强制终止并抛 TIMEOUT */
    private static void runProcess(List<String> command, long timeoutMs, String label)
            throws IOException, InterruptedException {
        runProcess(command, timeoutMs, label, null);
    }

    /**
     * 启动子进程并等待结束。
     *
     * @param stdinFile 非空时将进程标准输入重定向到该文件（restore 用）
     */
    private static void runProcess(List<String> command, long timeoutMs, String label, Path stdinFile)
            throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        if (stdinFile != null) {
            builder.redirectInput(stdinFile.toFile());
        }
        Process process = builder.start();
        String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        boolean finished = process.waitFor(timeoutMs, TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new TestSupportException("TIMEOUT", label + " timed out after " + timeoutMs + "ms");
        }
        if (process.exitValue() != 0) {
            String message = output.isBlank() ? label + " exited with code " + process.exitValue() : output.trim();
            throw new TestSupportException("INTERNAL_ERROR", message);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    /** 非法表名直接拒绝，避免进入 mysqldump 参数列表 */
    private void validateTableName(String table) {
        if (!TABLE_NAME_PATTERN.matcher(table).matches()) {
            throw new TestSupportException("BAD_REQUEST", "invalid table name: " + table);
        }
    }

    /** mysqldump / mysql 连接参数（从 JDBC 配置解析而来） */
    @Getter
    private static final class ConnectionInfo {
        private final String host;
        private final int port;
        private final String database;
        private final String username;
        private final String password;

        ConnectionInfo(String host, int port, String database, String username, String password) {
            this.host = host;
            this.password = password;
            this.port = port;
            this.database = database;
            this.username = username;
        }
    }
}
