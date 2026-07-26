package com.demo.scenario.service.impl;

import com.alibaba.fastjson2.JSON;
import com.demo.common.exception.ServiceException;
import com.demo.common.utils.StringUtils;
import com.demo.scenario.domain.TestScenarioItem;
import com.demo.scenario.domain.TestScenarioManifest;
import com.demo.scenario.result.TestScenarioLoadResult;
import com.demo.scenario.service.ITestScenarioService;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptException;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 通过 JDBC 执行 classpath 下 seed SQL，准备测试场景数据
 */
@Service
public class TestScenarioServiceImpl implements ITestScenarioService {

    private static final String SEED_PREFIX = "sql/seed/";

    private static final List<String> BASELINE_SCRIPTS = List.of(
            "00_truncate_business.sql",
            "01_base_seed.sql",
            "02_account_seed.sql"
    );

    private final DataSource dataSource;

    private TestScenarioManifest manifest;

    private Map<String, TestScenarioItem> scenarioById = Collections.emptyMap();

    private final AtomicReference<String> currentScenarioId = new AtomicReference<>();

    public TestScenarioServiceImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void initManifest() {
        try (InputStream in = seedResource("scenarios/manifest.json").getInputStream()) {
            manifest = JSON.parseObject(in, TestScenarioManifest.class);
        } catch (IOException e) {
            throw new IllegalStateException("读取测试场景 manifest 失败: " + e.getMessage(), e);
        }
        if (manifest.getScenarios() == null || manifest.getScenarios().isEmpty()) {
            throw new IllegalStateException("测试场景 manifest 中 scenarios 为空");
        }
        scenarioById = manifest.getScenarios().stream()
                .collect(Collectors.toMap(
                        item -> normalizeId(item.getId()),
                        Function.identity(),
                        (a, b) -> a
                ));
    }

    @Override
    public List<TestScenarioItem> listScenarios() {
        return manifest.getScenarios();
    }

    @Override
    public String getCurrentScenarioId() {
        return currentScenarioId.get();
    }

    @Override
    public TestScenarioLoadResult resetBaseline() {
        long start = System.currentTimeMillis();
        runBaselineScripts();
        currentScenarioId.set(null);
        return TestScenarioLoadResult.builder()
                .resetExecuted(true)
                .elapsedMs(System.currentTimeMillis() - start)
                .build();
    }

    @Override
    public TestScenarioLoadResult loadScenario(String scenarioId, boolean autoReset) {
        TestScenarioItem item = requireScenario(scenarioId);
        long start = System.currentTimeMillis();
        boolean resetExecuted = false;
        if (autoReset) {
            runBaselineScripts();
            resetExecuted = true;
        }
        executeSql(item.getFile());
        currentScenarioId.set(item.getId());
        return TestScenarioLoadResult.builder()
                .scenarioId(item.getId())
                .name(item.getName())
                .resetExecuted(resetExecuted)
                .elapsedMs(System.currentTimeMillis() - start)
                .build();
    }

    private void runBaselineScripts() {
        for (String script : BASELINE_SCRIPTS) {
            executeSql(script);
        }
    }

    private TestScenarioItem requireScenario(String scenarioId) {
        if (StringUtils.isEmpty(scenarioId)) {
            throw new ServiceException("场景 ID 不能为空");
        }
        TestScenarioItem item = scenarioById.get(normalizeId(scenarioId));
        if (item == null) {
            throw new ServiceException("未知测试场景: " + scenarioId);
        }
        return item;
    }

    private String normalizeId(String scenarioId) {
        return scenarioId.trim().toUpperCase(Locale.ROOT);
    }

    private void executeSql(String relativePath) {
        if (StringUtils.isEmpty(relativePath)) {
            throw new ServiceException("场景 SQL 路径为空");
        }
        String normalized = relativePath.replace('\\', '/');
        try (Connection connection = dataSource.getConnection()) {
            EncodedResource encodedResource = new EncodedResource(seedResource(normalized), StandardCharsets.UTF_8);
            ScriptUtils.executeSqlScript(connection, encodedResource);
        } catch (ScriptException | SQLException e) {
            throw new ServiceException("执行 SQL 失败 [" + normalized + "]: " + e.getMessage());
        }
    }

    private ClassPathResource seedResource(String relativePath) {
        String normalized = relativePath.replace('\\', '/');
        ClassPathResource resource = new ClassPathResource(SEED_PREFIX + normalized);
        if (!resource.exists()) {
            throw new ServiceException("seed 资源不存在: " + SEED_PREFIX + normalized + "（请先编译 demo-system 模块）");
        }
        return resource;
    }
}
