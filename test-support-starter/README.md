# qualitest-test-support-spring-boot-starter

> **进阶文档**：被测系统（JVM / Spring Boot）接入质衡「节点级 checkpoint + 失败后还原」能力。  
> 能力摘要见 [质衡开源与工程路线图 §5](../../qualitest/docs/质衡开源与工程路线图.md#5-已交付能力被测数据快照与还原重试)。

Starter 负责暴露 HTTP 端点、**非生产 profile 硬禁用**与超时包装；**具体怎么备份/还原**由你实现 `SnapshotStrategy`。**不做 token 鉴权**——安全边界是「生产环境根本不注册端点」。

---

## 适用场景

- 被测系统是 Spring Boot 应用，测试流含写操作（下单、改库存等），节点失败后需要把被测库还原到 checkpoint 之前。
- 质衡**不直连被测库**，只通过 HTTP 调用本 Starter 提供的 `/test-support/*` 端点。
- 非 JVM 被测系统：不依赖本 Starter，按 [端点契约](#端点契约) 自行实现 HTTP 接口即可。

---

## 生产环境禁用（核心护栏）

端点**仅在测试/开发环境存在**，不靠 token 挡人，靠 **Spring Profile 不注册 Bean**：

| 条件 | 说明 |
|------|------|
| `@Profile("!prod & !production & !prd")` | 激活 `prod` / `production` / `prd` 任一 profile 时，AutoConfiguration **不加载**，无 Controller、无路由 |
| `qualitest.test-support.enabled=true` | 显式开关；默认 `false` |
| 存在 `SnapshotStrategy` Bean | 用户实现备份逻辑 |

生产部署应使用 `spring.profiles.active=prod`（或 `production` / `prd`），并确保 `enabled=false`。即使误配 `enabled=true`，profile 护栏仍使端点不可达（请求会 404）。

## 并发约定

还原会覆盖被测数据。**开启数据还原（质衡侧 `allowDestructiveReset`）时，同一环境请串行跑**；不要让多条带 checkpoint 的 Run 并行打同一被测库。

---

## 快速接入

### 1. 添加依赖

```xml
<dependency>
    <groupId>com.qualitest</groupId>
    <artifactId>qualitest-test-support-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

本地开发可先 `mvn install`（本仓库位于 `qualitest-demo/test-support-starter`）。

### 2. 配置

```yaml
# application-dev.yml（开发/测试，默认 profile）
qualitest:
  test-support:
    enabled: true
    default-timeout-ms: 30000
```

```yaml
# application-prod.yml（生产）
qualitest:
  test-support:
    enabled: false
```

### 3. 实现 `SnapshotStrategy`

```java
@Component
public class MySnapshotStrategy implements SnapshotStrategy {

    @Override
    public SnapshotResult snapshot(SnapshotCommand cmd) {
        // 按 cmd.getScope() / cmd.getTables() 做表级备份，必须足够轻量
        // cmd.getLabel() 通常为 runId:nodeId，便于日志关联
        return SnapshotResult.builder()
                .snapshotId("snap-...")
                .createdAt(Instant.now())
                .scope(cmd.getScope())
                .status("ready")
                .build();
    }

    @Override
    public void restore(String snapshotId) {
        // 幂等：同一 snapshotId 多次 restore 结果一致
    }
}
```

> **性能硬约束**：checkpoint 可能在单次 Run 内多次调用。务必使用**表级 scope** + 轻量策略（savepoint / 影子表 / 表级 dump），避免每节点全库 dump。

---

## 端点契约

基路径示例：`http://your-app:8081/test-support`（质衡环境配置为 `resetEndpoint`）。

### `POST /test-support/snapshot`

请求：

```json
{
  "scope": "tables",
  "tables": ["mall_order", "mall_product_sku"],
  "label": "run-1893:node-7",
  "meta": { "env": "test", "nodeName": "创建订单" }
}
```

响应 `200`：

```json
{
  "snapshotId": "snap-…",
  "createdAt": "2026-07-01T12:00:00Z",
  "scope": "tables",
  "status": "ready"
}
```

### `POST /test-support/restore`

请求：`{ "snapshotId": "snap-…" }`  
响应 `200`：`{ "snapshotId": "snap-…", "status": "restored" }`

### `snapshotId` 由谁保存？

| 角色 | 职责 |
|------|------|
| **质衡 Run** | snapshot 成功后把 `snapshotId` 压入快照栈 `[(nodeId, snapshotId)]`；失败还原时把栈顶的 `snapshotId` 传给 restore |
| **被测系统** | 只负责按 `snapshotId` 落盘/读盘，**不在被测库维护元数据表** |

被测方实现可自行约定文件路径规则；demo 参考实现为 `{SNAPSHOT_DIR}/{snapshotId}.sql`（`snapshotId` 格式 `snap-{uuid}`）。

### 其它约定

| 项 | 要求 |
|----|------|
| 生产禁用 | `prod` / `production` / `prd` profile 下不暴露端点 |
| 错误 | `{ "code": "...", "message": "..." }` + 合适 HTTP 状态码 |
| 幂等 | 同一 `snapshotId` 多次 restore 结果一致 |
| 存活探测 | 不单独提供 `/health`；snapshot 调用失败即视为不可达 |

完整契约与护栏说明见本文 [端点契约](#端点契约)；能力摘要见 [质衡路线图 · 已交付能力](../../qualitest/docs/质衡开源与工程路线图.md#5-已交付能力被测数据快照与还原重试)。

---

## curl 验收

不启动质衡，仅被测应用 + 本 Starter 即可自测（**无需鉴权头**）：

```bash
# 1. 打快照
curl -s -X POST http://localhost:8081/test-support/snapshot \
  -H "Content-Type: application/json" \
  -d '{"scope":"tables","tables":["mall_order","mall_product_sku"],"label":"manual-test:node-1"}'

# 2. 执行业务写操作改脏数据

# 3. 还原
curl -s -X POST http://localhost:8081/test-support/restore \
  -H "Content-Type: application/json" \
  -d '{"snapshotId":"<上一步返回的 snapshotId>"}'

# 4. 验证数据已回滚；重复 restore 应幂等
```

生产 profile 下上述路径应 **404**（端点未注册）。

---

## 参考实现（qualitest-demo）

本仓库 [qualitest-demo](../) 已依赖本 Starter 并自产自吃：

| 组件 | 路径 |
|------|------|
| Starter 模块 | `qualitest-demo/test-support-starter/` |
| Demo Strategy | `demo-system/.../MysqldumpSnapshotStrategy.java`（mysqldump 路径等在类内常量配置） |
| 开发配置 | `demo-admin/.../application-dev.yml` |
| 生产配置 | `demo-admin/.../application-prod.yml`（`spring.profiles.active=prod`） |
| Spring Security | `demo-framework/.../SecurityConfig.java` → `/test-support/**` permitAll |

`MysqldumpSnapshotStrategy` 行为：

- **snapshot**：`mysqldump` 写出 `{SNAPSHOT_DIR}/{snapshotId}.sql`，响应返回 `snapshotId`（由调用方保存，demo 侧不写库表）。
- **restore**：按传入的 `snapshotId` 定位同名 `.sql` 并 `mysql` 导入；同一 `snapshotId` 可重复 restore。
- **范围**：未带 `tables` 时整库导出；请求体带 `tables` 时仅导出指定表。
- **配置**：`MYSQL_BIN_DIR`、`SNAPSHOT_DIR`、超时等在类内常量，不读 `application.yml`。

启动 demo（端口 8081）后，用上方 curl 即可验收阶段 A（curl 场景下由你自行保存上一步返回的 `snapshotId`）。

---

## 相关文档

| 文档 | 说明 |
|------|------|
| [质衡开源与工程路线图 §5](../../qualitest/docs/质衡开源与工程路线图.md#5-已交付能力被测数据快照与还原重试) | 已交付能力摘要与串行约定 |
| [qualitest-demo README](../README.md) | 靶场快速启动；test-support 细节以本文为准 |
