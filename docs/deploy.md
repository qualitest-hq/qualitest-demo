# 质衡 Demo 部署说明（独立 Compose 全栈）

与主仓 [`qualitest`](https://github.com/qualitest-hq/qualitest) **并行独立 Compose**：主仓 **不会** 用 `--profile demo` 拉起本仓；两边各起即可联调。本仓库表靠 initdb dump + 场景 seed，**不使用 Flyway**（Flyway 仅质衡主仓）。

默认宿主机端口（避开主仓 5180/3306/6379；Web 容器内 Nginx 亦为 **5181**）：

| 服务 | 默认端口 |
|------|----------|
| 管理端 UI（Nginx） | **5181** |
| API / Swagger | **8801** |
| MySQL | **3307** |
| Redis | **6380** |
| RustFS（可选） | **9000** / **9001** |

与质衡联调时：两边都在本机进程跑，环境 `baseUrl` 用 `http://localhost:8801`；质衡 **app 在 Compose 容器内**时改为 `http://host.docker.internal:8801`（主仓 compose 已配置该主机名）。完整步骤见主仓 [docs/deploy.md](https://github.com/qualitest-hq/qualitest/blob/main/docs/deploy.md)。本机 `dev` 的 MySQL 口令默认 `root` / `123456`；Docker `.env` 的 `MYSQL_ROOT_PASSWORD` 默认 `qualitest`，不要混用。

## 甲、不用 Docker

本机已有 JDK 17、Maven、Node ≥ 22.13、pnpm ≥ 11、MySQL 8、Redis。靶场没有 Flyway，必须导入两份 SQL。可与质衡共用一台 MySQL（库名 `qualitest-demo`）和一台 Redis（应用使用逻辑库 **11**）。

建库：

```sql
CREATE DATABASE IF NOT EXISTS `qualitest-demo`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
```

**Linux / macOS / Git Bash**

```bash
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
mysql -uroot -p123456 qualitest-demo < deploy/mysql/docker-entrypoint-initdb.d/01-qualitest-demo.sql
mysql -uroot -p123456 qualitest-demo < deploy/mysql/docker-entrypoint-initdb.d/02_business_menus.sql
mvn -pl demo-admin -am spring-boot:run -DskipTests
```

**Windows PowerShell**

```powershell
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
Get-Content -Raw deploy\mysql\docker-entrypoint-initdb.d\01-qualitest-demo.sql | mysql -uroot -p123456 qualitest-demo
Get-Content -Raw deploy\mysql\docker-entrypoint-initdb.d\02_business_menus.sql | mysql -uroot -p123456 qualitest-demo
mvn -pl demo-admin -am spring-boot:run -DskipTests
```

**Windows 命令提示符**

```bat
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
mysql -uroot -p123456 qualitest-demo < deploy\mysql\docker-entrypoint-initdb.d\01-qualitest-demo.sql
mysql -uroot -p123456 qualitest-demo < deploy\mysql\docker-entrypoint-initdb.d\02_business_menus.sql
mvn -pl demo-admin -am spring-boot:run -DskipTests
```

另开终端：

```bash
cd demo-ui
pnpm install
pnpm dev
```

UI http://localhost:5181 ，Swagger http://localhost:8801/swagger-ui.html ，账号 `admin` / `admin123`。root 口令不是 `123456` 时改 `-p123456`，并在 `mvn` 前设置 `SPRING_DATASOURCE_DRUID_MASTER_PASSWORD`。

## 乙、Docker 一键

前置：Docker Desktop 已启动（或 Linux 上 Docker Engine + Compose V2），本机有 Git。按系统 **只复制对应那一段**。已经克隆过的，从 `cd` 那一行开始贴。质衡主仓的完整步骤（含端口冲突、`baseUrl`）见主仓 [docs/deploy.md](https://github.com/qualitest-hq/qualitest/blob/main/docs/deploy.md)。

**Linux / macOS / Git Bash**

```bash
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
chmod +x scripts/quick-start.sh
./scripts/quick-start.sh
```

**Windows（PowerShell 或命令提示符）**

```bat
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
scripts\quick-start.bat
```

GitHub 克隆很慢时改用只读镜像（目录名仍是 `qualitest-demo`）：`https://gitee.com/qualitest-hq/qualitest-demo.git`。

脚本会：没有 `.env` 时从 `.env.example` 复制；先拉 GHCR；拉取失败则自动本地构建。拉取停住时 `Ctrl+C`，再执行 `docker compose up -d --build`。

确认：

```bash
docker compose ps
```

`qualitest-demo-web` 为 Up 后打开下面的地址。

不用脚本时，与上面二选一（已有 `.env` 不会覆盖）：

**Linux / macOS / Git Bash**

```bash
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
cp -n .env.example .env
docker compose pull mysql app web
docker compose up -d
docker compose ps
curl -fsS -D - -o /dev/null http://localhost:5181/
```

**Windows PowerShell**

```powershell
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
if (-not (Test-Path .env)) { Copy-Item .env.example .env }
docker compose pull mysql app web
docker compose up -d
docker compose ps
curl.exe -fsS -D - -o NUL http://localhost:5181/
```

**Windows 命令提示符**

```bat
git clone https://github.com/qualitest-hq/qualitest-demo.git
cd qualitest-demo
if not exist .env copy /Y .env.example .env
docker compose pull mysql app web
docker compose up -d
docker compose ps
curl.exe -fsS -D - -o NUL http://localhost:5181/
```

`pull` 失败时执行 `docker compose up -d --build`。响应头为 `HTTP/1.1 200` 后打开 UI。官方镜像：

- `ghcr.io/qualitest-hq/qualitest-demo-app`
- `ghcr.io/qualitest-hq/qualitest-demo-web`
- `ghcr.io/qualitest-hq/qualitest-demo-mysql`（内置 initdb SQL，空数据卷首次启动自动导入）

（`latest` + `sha-<短提交>`；仅 `qualitest-hq/qualitest-demo` 的 `main` / 手动触发 GHCR workflow。）

改代码时在仓库目录执行 `docker compose up -d --build`。

- UI：**http://localhost:5181**，账号 **`admin` / `admin123`**（**仅本地 / 私有环境**）；另有 **`demo` / `demo123`**（切场景用）
- **公网演示环境**：执行运维仓 [`demo-seed-target.sql`](https://github.com/38680050/qualitest-demo-host/blob/master/sql/demo-seed-target.sql) 后，运维口令为 **`admin` / `QtDemo#Admin2026`**，切场景 **`demo` / `demo123`**（勿对外宣传入口；指南见 [qualitest-demo-host/1panel/GUIDE.md](https://github.com/38680050/qualitest-demo-host/blob/master/1panel/GUIDE.md)）。登录页生产构建**不预填**账号密码
- Swagger：**http://localhost:8801/swagger-ui.html**
- 质衡联调 `baseUrl`：本机多为 `http://localhost:8801`（容器内质衡见主仓 deploy）
- 库初始化：打进 `qualitest-demo-mysql` 镜像（[`01-qualitest-demo.sql`](../deploy/mysql/docker-entrypoint-initdb.d/01-qualitest-demo.sql) + [`02_business_menus.sql`](../deploy/mysql/docker-entrypoint-initdb.d/02_business_menus.sql)）；业务场景用管理端「测试场景」加载，**无 Flyway**
- 已有 `mysql_data` 卷不会再次跑 initdb：对该库执行 [`02_business_menus.sql`](../deploy/mysql/docker-entrypoint-initdb.d/02_business_menus.sql)，或 `docker compose down -v` 重灌（会清空数据）

生产务必修改 `.env` 中的 `MYSQL_ROOT_PASSWORD`、`TOKEN_SECRET`。

### 官方镜像（GHCR）

| 镜像 | 说明 |
|------|------|
| `ghcr.io/qualitest-hq/qualitest-demo-app` | 后端（Spring Boot） |
| `ghcr.io/qualitest-hq/qualitest-demo-web` | 前端（Nginx + SPA，反代 `/prod-api` → app） |
| `ghcr.io/qualitest-hq/qualitest-demo-mysql` | MySQL 8.0 + 内置库表/种子 initdb |

Packages：https://github.com/orgs/qualitest-hq/packages  

官方包为 **Public** 时可匿名 `docker pull`。首次若为 Private，在组织 Packages 页改为 Public。可用 `.env` 的 `DEMO_IMAGE_TAG=sha-<短提交>` 钉版本。

## 本机改代码（热更 · MySQL / Redis 仍用 Compose）

这不是「不用 Docker」。MySQL / Redis 仍由 Compose 提供。完全不用 Docker 见上文 **「甲、不用 Docker」**。

```bash
docker compose up -d mysql redis
```

本机连：`localhost:3307`（密码默认 `qualitest`）、`localhost:6380`，Redis DB `11`。然后：

```bash
mvn -pl demo-admin -am -DskipTests package
# 或 demo.bat / demo.sh（需已有 jar）

cd demo-ui && pnpm install && pnpm dev
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
| mysql | qualitest-demo-mysql | 镜像内 initdb（见 `deploy/mysql/`） |
| redis | qualitest-demo-redis | DB 11 |
| app | qualitest-demo-app | 镜像 `ghcr.io/qualitest-hq/qualitest-demo-app`；`profile=docker`，端口 8801 |
| web | qualitest-demo-web | 镜像 `ghcr.io/qualitest-hq/qualitest-demo-web`；Nginx + `/prod-api` → app |
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
