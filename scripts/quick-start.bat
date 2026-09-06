@echo off
REM 质衡 Demo 一键启动：Docker Compose 全栈（独立仓）
REM 用法：
REM   scripts\quick-start.bat           启动 MySQL + Redis + 后端 + Nginx
REM   scripts\quick-start.bat rustfs    同上，并启用可选 RustFS（含 app 客户端开关）
REM   scripts\quick-start.bat -h        显示本说明
REM 说明：从任意目录调用即可；依赖 Docker Desktop + Compose V2
REM 默认端口：Web 5181 / API 8801 / MySQL 3307 / Redis 6380（避开主仓）
chcp 65001 >nul
setlocal
cd /d "%~dp0.."

if /I "%~1"=="-h" goto :usage
if /I "%~1"=="--help" goto :usage
if /I "%~1"=="/?" goto :usage
if /I "%~1"=="help" goto :usage
if not "%~1"=="" if /I not "%~1"=="rustfs" (
  echo [error] 未知参数: %~1
  call :usage
  exit /b 1
)

where docker >nul 2>&1
if errorlevel 1 (
  echo [error] 未找到 docker，请先安装 Docker Desktop
  exit /b 1
)

docker compose version >nul 2>&1
if errorlevel 1 (
  echo [error] 需要 Docker Compose V2（docker compose）
  exit /b 1
)

if not exist ".env" (
  if exist ".env.example" (
    copy /Y ".env.example" ".env" >nul
    echo [info] 已从 .env.example 生成 .env
  )
)

echo [info] 构建并启动 MySQL + Redis + 后端 + Nginx ...
if /I "%~1"=="rustfs" (
  echo [info] 已启用可选 profile: rustfs（并加载 docker-compose.rustfs.yml）
  docker compose -f docker-compose.yml -f docker-compose.rustfs.yml --profile rustfs up -d --build
) else (
  docker compose up -d --build
)
if errorlevel 1 exit /b 1

echo.
echo ==============================================
echo  质衡 Demo 已启动
echo  管理端 UI:  http://localhost:5181
echo  API/Swagger: http://localhost:8801/swagger-ui.html
echo  默认账号:    admin / admin123
echo  停止:        docker compose down
echo  仅依赖:      docker compose up -d mysql redis
echo  含 RustFS:   scripts\quick-start.bat rustfs
if /I "%~1"=="rustfs" (
  echo  RustFS API:  http://localhost:9000  控制台: http://localhost:9001
  echo  默认密钥:    rustfsadmin / rustfsadmin
  echo  停 RustFS:   docker compose --profile rustfs down
)
echo ==============================================
endlocal
exit /b 0

:usage
echo 用法:
echo   scripts\quick-start.bat           启动全栈（MySQL + Redis + 后端 + Nginx）
echo   scripts\quick-start.bat rustfs    全栈 + 可选 RustFS（S3 API :9000 / 控制台 :9001）
echo   scripts\quick-start.bat -h        显示本说明
echo.
echo 默认端口: Web 5181 / API 8801 / MySQL 3307 / Redis 6380
echo 仅依赖:   docker compose up -d mysql redis
echo 仅 RustFS: docker compose --profile rustfs up -d rustfs
echo 停止:     docker compose down
exit /b 0
