# 质衡 Demo 部署说明（独立 Compose 全栈）

与主仓 [`qualitest`](https://github.com/qualitest-hq/qualitest) **并行独立 Compose**：主仓 **不会** 用 `--profile demo` 拉起本仓；两边各起即可联调。本仓库表靠 initdb dump + 场景 seed，**不使用 Flyway**（Flyway 仅质衡主仓）。

默认宿主机端口（避开主仓 80/3306/6379）：

| 服务 | 默认端口 |
|------|----------|
| 管理端 UI（Nginx） | **8082** |
| API / Swagger | **8081** |
| MySQL | **3307** |
| Redis | **6380** |
| RustFS（可选） | **9000** / **9001** |

与质衡联调时，质衡项目环境 `baseUrl` 一般为 `http://localhost:8081`；若质衡 **app 跑在 Compose 容器内**，见主仓 [docs/deploy.md · 与靶场联调](https://github.com/qualitest-hq/qualitest/blob/main/docs/deploy.md)（`host.docker.internal`）。

## 一键全栈

前置：Docker Desktop / Compose V2。

```bash
# Linux / macOS
chmod +x scripts/quick-start.sh
./scripts/quick-start.sh

# Windows
scripts\quick-start.bat

# 或手动
docker compose up -d --build
```

- UI：**http://localhost:8082**，账号 **`admin` / `admin123`**
- Swagger：**http://localhost:8081/swagger-ui.html**
- 质衡联调 `baseUrl`：本机多为 `http://localhost:8081`（容器内质衡见主仓 deploy）
- 库初始化：`sql/qualitest-demo_*.sql` 挂 initdb；业务场景用管理端加载，**无 Flyway**

生产务必修改 `.env` 中的 `MYSQL_ROOT_PASSWORD`、`TOKEN_SECRET`。

## 仅依赖（本机开发）

```bash
docker compose up -d mysql redis
```

本机连：`localhost:3307`（密码默认 `qualitest`）、`localhost:6380`，Redis DB `11`。然后：

```bash
mvn -pl demo-admin -am -DskipTests package
# 或 demo.bat / demo.sh（需已有 jar）

cd demo-ui && npm install && npm run dev
```

本机开发若只需 RustFS（文件 API），可：

```bash
docker compose --profile rustfs up -d rustfs
```

并保持 `application-dev.yml` 中 `demo.rustfs.enabled=true`、`endpoint=http://127.0.0.1:9000`。

## 可选：RustFS（文件上传 API）

默认**不启动**。需要时二选一：

```bash
# 推荐：全栈 + RustFS（同时打开 app 客户端）
scripts\quick-start.bat rustfs
# ./scripts/quick-start.sh rustfs

# 等价手动命令
docker compose -f docker-compose.yml -f docker-compose.rustfs.yml --profile rustfs up -d --build
```

| 项 | 默认 |
|----|------|
| S3 API | http://localhost:9000 |
| 控制台 | http://localhost:9001 |
| Access / Secret | `rustfsadmin` / `rustfsadmin` |
| Bucket | `qualitest-demo`（首次可在控制台创建；demo 启动时也会尝试自动创建） |
| 容器内 endpoint | `http://rustfs:9000` |
| 浏览器/预签名 | `http://127.0.0.1:9000/qualitest-demo` |

说明：

- 仅 `docker compose --profile rustfs up -d` **只会起 RustFS 容器**，不会把 `DEMO_RUSTFS_ENABLED` 设为 true；全栈联调请用上面的 `-f docker-compose.rustfs.yml` 或 `quick-start … rustfs`。
- 端口冲突时改 `.env` 的 `RUSTFS_API_PORT` / `RUSTFS_CONSOLE_PORT`。

验收接口（需客户端 Token）：

- `POST /api/file/upload`
- `GET /api/file?key=`
- `DELETE /api/file?key=`

样例附件（若仓库含）：`docs/fixtures/`。

## 架构

| 服务 | 容器名 | 说明 |
|------|--------|------|
| mysql | qualitest-demo-mysql | 初始化：`sql/qualitest-demo_*.sql` |
| redis | qualitest-demo-redis | DB 11 |
| app | qualitest-demo-app | `profile=docker`，端口 8081 |
| web | qualitest-demo-web | Nginx + `/prod-api` → app |
| rustfs（可选） | qualitest-demo-rustfs | `--profile rustfs` + `docker-compose.rustfs.yml` |

## 常用命令

```bash
docker compose logs -f app
docker compose ps
docker compose down
docker compose --profile rustfs down   # 若曾启用 rustfs
docker compose down -v                 # 清空数据卷（含 rustfs_data，慎用）
```

## 相关文件

- [`docker-compose.yml`](../docker-compose.yml)
- [`docker-compose.rustfs.yml`](../docker-compose.rustfs.yml)
- [`Dockerfile`](../Dockerfile)
- [`deploy/docker/Dockerfile.web`](../deploy/docker/Dockerfile.web)
- [`.env.example`](../.env.example)
- [`application-docker.yml`](../demo-admin/src/main/resources/application-docker.yml)
- 测试指南：[compose-测试指南.md](./compose-测试指南.md)
