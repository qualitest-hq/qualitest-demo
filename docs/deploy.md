# 质衡 Demo 部署说明（独立 Compose 全栈）

与主仓 [`qualitest`](https://github.com/qualitest-hq/qualitest) **互不依赖**：本仓自带 MySQL / Redis / 后端 / 前端 Nginx；可选 RustFS。

默认宿主机端口（避开主仓 80/3306/6379）：

| 服务 | 默认端口 |
|------|----------|
| 管理端 UI（Nginx） | **8082** |
| API / Swagger | **8081** |
| MySQL | **3307** |
| Redis | **6380** |
| RustFS（可选） | 9000 / 9001 |

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
- 质衡联调 `baseUrl`：`http://localhost:8081`

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

## 可选：RustFS

默认不启动。需要文件上传 API 时：

```bash
docker compose --profile rustfs up -d --build
# 或
scripts\quick-start.bat rustfs
# ./scripts/quick-start.sh rustfs
```

`quick-start … rustfs` 会设置 `DEMO_RUSTFS_ENABLED=true`，容器内 endpoint 为 `http://rustfs:9000`。  
控制台 http://localhost:9001，密钥默认 `rustfsadmin` / `rustfsadmin`。

若主仓已起 RustFS 占用 9000，请改 `.env` 中 `RUSTFS_*_PORT`，或只用一边。

## 架构

| 服务 | 容器名 | 说明 |
|------|--------|------|
| mysql | qualitest-demo-mysql | 初始化：`sql/qualitest-demo_*.sql` |
| redis | qualitest-demo-redis | DB 11 |
| app | qualitest-demo-app | `profile=docker`，端口 8081 |
| web | qualitest-demo-web | Nginx + `/prod-api` → app |
| rustfs（可选） | qualitest-demo-rustfs | `--profile rustfs` |

## 常用命令

```bash
docker compose logs -f app
docker compose ps
docker compose down
docker compose down -v   # 清空数据卷（慎用）
```

## 相关文件

- [`docker-compose.yml`](../docker-compose.yml)
- [`Dockerfile`](../Dockerfile)
- [`deploy/docker/Dockerfile.web`](../deploy/docker/Dockerfile.web)
- [`.env.example`](../.env.example)
- [`application-docker.yml`](../demo-admin/src/main/resources/application-docker.yml)
