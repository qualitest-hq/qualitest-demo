# qualitest-demo

独立于主仓 [`qualitest`](https://github.com/qualitest-hq/qualitest) 的 **接口测试靶场**（商城业务 + 客户端完整流程）。默认端口 **8081**，库 **qualitest-demo**，Redis DB **11**。联调质衡时配置 `baseUrl = http://localhost:8081`。

## 相关仓库

| 仓库 | 说明 |
|------|------|
| [qualitest](https://github.com/qualitest-hq/qualitest) | 质衡主平台（含前端 `qualitest-ui/`） |
| [qualitest-intellij-plugin](https://github.com/qualitest-hq/qualitest-intellij-plugin) | IDEA 接口同步；示例工程可用本靶场 |

## 快速启动

### 方式 A · Docker Compose（推荐）

前置：Docker Desktop / Compose V2。详情见 [`docs/deploy.md`](./docs/deploy.md)。

```bash
# Windows
scripts\quick-start.bat
# Linux / macOS
chmod +x scripts/quick-start.sh && ./scripts/quick-start.sh
```

- 管理端 UI：**http://localhost:8082**（账号 **`admin` / `admin123`**）
- Swagger / 质衡 `baseUrl`：**http://localhost:8081**
- 默认宿主机端口避开主仓：MySQL **3307**、Redis **6380**
- 仅依赖：`docker compose up -d mysql redis`
- 可选 RustFS：`scripts\quick-start.bat rustfs`（详见 [`docs/deploy.md`](./docs/deploy.md)）

### 方式 B · 本机

1. 建库 `qualitest-demo`，执行 `sql/qualitest-demo_20260628_192719.sql`
2. 改 `demo-admin/.../application-dev.yml`，或复制 [`.env.example`](./.env.example) 为 `.env` 后用环境变量覆盖（生产务必改 `TOKEN_SECRET` / 库口令）
3. `mvn clean install`，启动 `demo-admin` 或运行 `demo.bat` / `demo.sh`

## 测接口（推荐流程）

1. 登录管理端 → 首页 **「进入测试场景控制台」**（或 **系统工具 → 测试场景**）
2. 点 **「加载此场景」**（自动 reset 基线 + 写入数据；共 S01–S08 / F01–F12）
3. 复制页面推荐账号，去 [Swagger](http://localhost:8081/swagger-ui.html) 或质衡跑用例

自动化可用 API：`POST /web/test/scenario/load/{id}`（需管理端 Token，默认 reset+load）。服务未启动时见 `sql/seed/` 下 bat 脚本。

**进阶**：

- 被测数据 snapshot/restore（供质衡 checkpoint 联调）见 **[test-support-starter 接入文档](test-support-starter/README.md)**（开启数据还原时，同一环境请串行跑）
- **进阶 · 文件 / RustFS**（插件扫 `form-data` `file` + 对象落盘）：
  1. 启动 RustFS：`scripts\quick-start.bat rustfs`（全栈+客户端）或本机仅 `docker compose --profile rustfs up -d rustfs`（默认 `http://127.0.0.1:9000`，`rustfsadmin` / `rustfsadmin`，bucket `qualitest-demo`）
  2. Compose 全栈 + rustfs 时由 `docker-compose.rustfs.yml` 注入 `DEMO_RUSTFS_ENABLED=true`；本机跑后端时确认 `application-dev.yml` 中 `demo.rustfs.enabled=true`
  3. 客户端 Token 调用：
     - `POST /api/file/upload`（multipart：`file` 必填 + `bizType` 可选）
     - `GET /api/file?key=`（元信息 / 预签名 URL）
     - `DELETE /api/file?key=`（清理）
  4. 样例附件：[docs/fixtures/sample-avatar.png](docs/fixtures/sample-avatar.png)

## 认证说明

| 端 | 前缀 | 登录 |
|---|---|---|
| 管理端 | `/web/**`、`/system/**` 等 | `POST /login` → Bearer **adminToken** |
| 客户端 | `/api/**` | `POST /api/account/auth/login` → Bearer **accountToken**（质衡里常用 `flow.token`） |

两端 Token **不可混用**；客户端接口从 Token 取 `accountId`，勿信请求体里的账号 ID。免登录：方法/类 `@Anonymous`，另有 `/login`、`/register`、`/captchaImage`、`/test-support/**` 等 path 白名单。

**联调质衡时注意**：插件「项目级上传」在质衡 `auth_config` 为空时只会种子**通用单套** Bearer（不分端、不带匿名 path）。本靶场双端 JWT 请到测试项目鉴权配置手工贴 **双端参考模板**（质衡 `ProjectAuthConfigSupport.dualBearerTemplate` / [`鉴权注入与Bearer方案.md`](https://github.com/qualitest-hq/qualitest/blob/main/docs/%E9%89%B4%E6%9D%83%E6%B3%A8%E5%85%A5%E4%B8%8EBearer%E6%96%B9%E6%A1%88.md) §4.1）：`/api/` → `flow.token`，`/system|/monitor|/tool|/web/` → `flow.adminToken`，并带上上述匿名 path。

## Swagger 与质衡

- 文档：[swagger-ui.html](http://localhost:8081/swagger-ui.html)（admin / api / tool 三组）
- 上传接口到质衡：IDEA 装 Qualitest Helper，扫 Controller（`@api.group`）上传到 `http://localhost:8080`；上传后按上一节补齐双端鉴权配置再造流

## AI 测试流自然语言

在质衡测试流画布中使用 **AI 设计助手** 时，见 **[docs/ai-test-flow-prompts.md](docs/ai-test-flow-prompts.md)**：先将文档中标注的**测试场景**在靶场加载，再把「加载场景 + 提示正文」粘贴到对话框。账号、SPU/SKU/券 ID、停用状态等 AI 无法推断的信息已写入提示正文。

使用前请先将 demo 接口上传到质衡测试项目，并将环境 `baseUrl` 指向 `http://localhost:8081`。

## 参考

- Compose 部署：[docs/deploy.md](./docs/deploy.md)
- Compose 测试指南：[docs/compose-测试指南.md](./docs/compose-测试指南.md)
- [AI 测试流提示集](docs/ai-test-flow-prompts.md)
- 场景元数据：`sql/seed/scenarios/manifest.json`
- 状态枚举、金额公式、验收用例链：见各场景 SQL 头部注释与 Swagger 字段说明
- test-support 快照/还原：[test-support-starter/README.md](test-support-starter/README.md)
