# qualitest-demo

独立于 `qualitest` 的 **接口测试靶场**（商城业务 + 客户端完整流程）。默认端口 **8081**，库 **qualitest-demo**，Redis DB **11**。联调质衡时配置 `baseUrl = http://localhost:8081`。

## 快速启动

1. 建库 `qualitest-demo`，执行 `sql/qualitest-demo_20260628_192719.sql`
2. 改 `demo-admin/src/main/resources/application-dev.yml` 数据库账号（生产用 `application-prod.yml`）
3. `mvn clean install`，启动 `demo-admin` 或运行 `demo.bat` / `demo.sh`

## 测接口（推荐流程）

1. 登录管理端 → 首页 **「进入测试场景控制台」**（或 **系统工具 → 测试场景**）
2. 点 **「加载此场景」**（自动 reset 基线 + 写入数据；共 S01–S08 / F01–F12）
3. 复制页面推荐账号，去 [Swagger](http://localhost:8081/swagger-ui.html) 或质衡跑用例

自动化可用 API：`POST /web/test/scenario/load/{id}`（需管理端 Token，默认 reset+load）。服务未启动时见 `sql/seed/` 下 bat 脚本。

**进阶**：

- 被测数据 snapshot/restore（供质衡 checkpoint 联调）见 **[test-support-starter 接入文档](test-support-starter/README.md)**（开启数据还原时，同一环境请串行跑）
- **进阶 · 文件 / RustFS**（插件扫 `form-data` `file` + 对象落盘）：
  1. 先启动本机 **RustFS**（S3 兼容，默认 `http://127.0.0.1:9000`，账号/密钥 `rustfsadmin`，bucket `qualitest-demo`）
  2. 确认 `application-dev.yml` 中 `demo.rustfs.enabled=true` 与上述凭据一致
  3. 客户端 Token 调用：
     - `POST /api/file/upload`（multipart：`file` 必填 + `bizType` 可选）
     - `GET /api/file?key=`（元信息 / 预签名 URL）
     - `DELETE /api/file?key=`（清理）
  4. 样例附件：[docs/fixtures/sample-avatar.png](docs/fixtures/sample-avatar.png)

## 认证说明

| 端 | 前缀 | 登录 |
|---|---|---|
| 管理端 | `/web/**` | `POST /login` → Bearer adminToken |
| 客户端 | `/api/**` | `POST /api/account/auth/login` → Bearer accountToken |

两端 Token **不可混用**；客户端接口从 Token 取 `accountId`，勿信请求体里的账号 ID。

## Swagger 与质衡

- 文档：[swagger-ui.html](http://localhost:8081/swagger-ui.html)（admin / api / tool 三组）
- 上传接口到质衡：IDEA 装 Qualitest Helper，扫 Controller（`@api.group`）上传到 `http://localhost:8080`

## AI 测试流自然语言

在质衡测试流画布中使用 **AI 设计助手** 时，见 **[docs/ai-test-flow-prompts.md](docs/ai-test-flow-prompts.md)**：先将文档中标注的**测试场景**在靶场加载，再把「加载场景 + 提示正文」粘贴到对话框。账号、SPU/SKU/券 ID、停用状态等 AI 无法推断的信息已写入提示正文。

使用前请先将 demo 接口上传到质衡测试项目，并将环境 `baseUrl` 指向 `http://localhost:8081`。

## 参考

- [AI 测试流提示集](docs/ai-test-flow-prompts.md)
- 场景元数据：`sql/seed/scenarios/manifest.json`
- 状态枚举、金额公式、验收用例链：见各场景 SQL 头部注释与 Swagger 字段说明
- test-support 快照/还原：[test-support-starter/README.md](test-support-starter/README.md)
