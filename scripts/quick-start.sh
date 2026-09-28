#!/usr/bin/env bash
# 质衡 Demo 一键启动：Docker Compose 全栈（独立仓）
#
# 用法：
#   ./scripts/quick-start.sh           # 启动 MySQL + Redis + 后端 + Nginx
#   ./scripts/quick-start.sh rustfs    # 同上，并启用可选 RustFS（含 app 客户端开关）
#   ./scripts/quick-start.sh -h        # 显示本说明
#
# 说明：从任意目录调用即可；依赖 Docker Engine/Desktop + Compose V2
# 默认端口：Web 5181 / API 8801 / MySQL 3307 / Redis 6380（避开主仓）
# 优先拉 GHCR；不可达时回退本地 --build
set -euo pipefail

usage() {
  cat <<'EOF'
用法:
  ./scripts/quick-start.sh           启动全栈（MySQL + Redis + 后端 + Nginx）
  ./scripts/quick-start.sh rustfs    全栈 + 可选 RustFS（S3 API :9000 / 控制台 :9001）
  ./scripts/quick-start.sh -h        显示本说明

默认端口: Web 5181 / API 8801 / MySQL 3307 / Redis 6380
官方镜像: ghcr.io/qualitest-hq/qualitest-demo-app|web|mysql
仅依赖:   docker compose up -d mysql redis
仅 RustFS: docker compose --profile rustfs up -d rustfs
停止:     docker compose down
EOF
}

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"

case "${1:-}" in
  -h|--help|help)
    usage
    exit 0
    ;;
  rustfs|"")
    ;;
  *)
    echo "[error] 未知参数: $1" >&2
    usage
    exit 1
    ;;
esac

if ! command -v docker >/dev/null 2>&1; then
  echo "[error] 未找到 docker，请先安装 Docker Desktop / Docker Engine"
  exit 1
fi

if ! docker compose version >/dev/null 2>&1; then
  echo "[error] 需要 Docker Compose V2（docker compose）"
  exit 1
fi

if [[ ! -f .env ]]; then
  if [[ -f .env.example ]]; then
    cp .env.example .env
    echo "[info] 已从 .env.example 生成 .env（请按需修改 MYSQL_ROOT_PASSWORD / TOKEN_SECRET）"
  fi
fi

WEB_PORT=5181
APP_PORT=8801
if [[ -f .env ]]; then
  WEB_PORT="$(grep -E '^WEB_PORT=' .env | tail -n1 | cut -d= -f2- || true)"
  WEB_PORT="${WEB_PORT:-5181}"
  APP_PORT="$(grep -E '^APP_PORT=' .env | tail -n1 | cut -d= -f2- || true)"
  APP_PORT="${APP_PORT:-8801}"
fi

compose_up() {
  local build_flag="${1:-}"
  if [[ "${2:-}" == "rustfs" ]]; then
    # shellcheck disable=SC2086
    docker compose -f docker-compose.yml -f docker-compose.rustfs.yml --profile rustfs up -d ${build_flag}
  else
    # shellcheck disable=SC2086
    docker compose up -d ${build_flag}
  fi
}

echo "[info] 拉取 GHCR 预构建镜像（ghcr.io/qualitest-hq/qualitest-demo-app|web|mysql）..."
if docker compose pull mysql app web; then
  echo "[info] 启动 MySQL + Redis + 后端 + Nginx ..."
  if [[ "${1:-}" == "rustfs" ]]; then
    echo "[info] 已启用可选 profile: rustfs（并加载 docker-compose.rustfs.yml）"
    compose_up "" rustfs
  else
    compose_up ""
  fi
else
  echo "[warn] pull 失败（镜像未发布 / 网络），改为本地构建 ..."
  if [[ "${1:-}" == "rustfs" ]]; then
    echo "[info] 已启用可选 profile: rustfs（并加载 docker-compose.rustfs.yml）"
    compose_up "--build" rustfs
  else
    compose_up "--build"
  fi
fi

echo
echo "=============================================="
echo " 质衡 Demo 已启动"
echo " 管理端 UI:   http://localhost:${WEB_PORT}"
echo " API/Swagger: http://localhost:${APP_PORT}/swagger-ui.html"
echo " 默认账号:    admin / admin123"
echo " 停止:        docker compose down"
echo " 仅依赖:      docker compose up -d mysql redis"
echo " 含 RustFS:   ./scripts/quick-start.sh rustfs"
if [[ "${1:-}" == "rustfs" ]]; then
  echo " RustFS API:  http://localhost:${RUSTFS_API_PORT:-9000}"
  echo " 控制台:      http://localhost:${RUSTFS_CONSOLE_PORT:-9001}"
  echo " 默认密钥:    rustfsadmin / rustfsadmin"
  echo " 停 RustFS:   docker compose --profile rustfs down"
fi
echo "=============================================="
