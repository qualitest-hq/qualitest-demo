# Compose 全栈测试指南（qualitest-demo）

> 用途：验收「Docker Compose 一键起靶场」。  
> 关联：[`deploy.md`](./deploy.md) · [`docker-compose.yml`](../docker-compose.yml) · [`scripts/quick-start.bat`](../scripts/quick-start.bat)

---

## 0. 前置检查

| 项 | 要求 |
|----|------|
| Docker | Desktop 已安装并运行 |
| Compose | `docker compose version`（V2） |
| 端口 | **5181 / 8801 / 3307 / 6380** 尽量空闲；测 RustFS 另需 **9000 / 9001** |
| 代码 | 在 `qualitest-demo/` 根目录 |

```bat
cd /d d:\Project\Composite\qualitest-all\qualitest-demo
docker compose version
```

---

## 1. 一键全栈

```bat
scripts\quick-start.bat
```

或：`docker compose up -d --build`

**预期：** mysql / redis / app / web 均为 `running`（app 建议 `healthy`）。

### 浏览器

1. UI：**http://localhost:5181** → `admin` / `admin123`
2. Swagger：**http://localhost:8801/swagger-ui.html**
3. 登录后能进「测试场景」或系统菜单

### 停掉

```bat
docker compose down
```

---

## 2. 可选：RustFS

```bat
scripts\quick-start.bat rustfs
```

**预期：**

- `qualitest-demo-rustfs` 在跑
- 控制台 http://localhost:9001 可登录 `rustfsadmin` / `rustfsadmin`
- S3 API http://localhost:9000
- app 环境含 `DEMO_RUSTFS_ENABLED=true`（`docker compose … config` 或 `docker inspect` 可核对）

仅起对象存储（本机 mvn 用）：

```bat
docker compose --profile rustfs up -d rustfs
```

---

## 3. 仅依赖

```bat
docker compose up -d mysql redis
```

本机：`localhost:3307`（密码默认 `qualitest`）、`localhost:6380`，Redis DB `11`。

---

## 4. 常见失败

| 现象 | 处理 |
|------|------|
| 端口占用 | 改 `.env` 的 `WEB_PORT` / `MYSQL_PORT` / `REDIS_PORT` / `RUSTFS_*_PORT` |
| app unhealthy | `docker compose logs app`；等 mysql healthy |
| 文件 API 失败但 RustFS 已起 | 须用 `quick-start rustfs` 或带上 `-f docker-compose.rustfs.yml`，否则 app 未开客户端 |
| 登录密码不对 | `docker compose down -v` 后重来（慎用） |

---

## 5. 验收清单

- [ ] `scripts\quick-start.bat` 成功；http://localhost:5181 可登录
- [ ] Swagger http://localhost:8801/swagger-ui.html 可开
- [ ] （可选）`scripts\quick-start.bat rustfs`：控制台 9001 可登录
- [ ] （可选）客户端 Token 调 `POST /api/file/upload` 成功
- [ ] `docker compose up -d mysql redis` 仅依赖可用
- [ ] `docker compose down` 能停干净
