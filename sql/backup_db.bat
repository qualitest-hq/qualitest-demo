@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

REM ============================================================================
REM qualitest 数据库备份脚本
REM 功能 - 使用 mysqldump 导出数据库并生成带时间戳的 SQL 文件并尝试加入 git
REM 用法 - 双击运行或在命令行执行 backup_db.bat
REM 输出 - 脚本同目录下生成 qualitest_yyyyMMdd_HHmmss.sql
REM 编码 - 本文件为 UTF-8 BOM 换行符须为 CRLF
REM ============================================================================

REM 切换到脚本所在目录避免双击时备份文件写到错误位置
cd /d "%~dp0"

REM ---------- 数据库连接配置按实际环境修改 ----------
set "DB_HOST=127.0.0.1"
set "DB_PORT=3306"
set "DB_USER=root"
set "DB_PASS=123456"
set "DB_NAME=qualitest-demo"

REM 临时连接配置文件 使用后自动删除 避免命令行 -p 触发 insecure 警告
set "DB_CNF=%~dp0_db_session.cnf"

REM ---------- MySQL 客户端路径按实际安装路径修改 ----------
set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server 8.0\bin"
set "MYSQLDUMP=%MYSQL_BIN%\mysqldump.exe"

if not exist "%MYSQLDUMP%" (
    echo [错误] 未找到 mysqldump
    echo   路径: %MYSQLDUMP%
    echo   请修改脚本顶部的 MYSQL_BIN
    goto fail
)

REM 生成时间戳作为文件名后缀依赖 PowerShell Win10 及以上通常已内置
for /f "usebackq delims=" %%G in (`powershell -NoProfile -Command "Get-Date -Format yyyyMMdd_HHmmss"`) do set "BACKUP_TS=%%G"
if not defined BACKUP_TS (
    echo [错误] 无法生成备份时间戳请确认本机已安装 PowerShell
    goto fail
)
set "BACKUP_FILE=%DB_NAME%_%BACKUP_TS%.sql"

echo 正在备份数据库 %DB_NAME% ...
echo 目标文件: %BACKUP_FILE%
echo.

call :write_db_cnf

REM 执行备份 stderr 直接输出到控制台不写入临时日志文件
"%MYSQLDUMP%" --defaults-extra-file="%DB_CNF%" %DB_NAME% --result-file="%BACKUP_FILE%"
if errorlevel 1 (
    echo.
    echo [错误] 备份失败请根据上方 mysqldump 输出排查原因
    del "%BACKUP_FILE%" 2>nul
    goto fail
)

REM 验证备份文件大小防止生成空文件
for %%F in ("%BACKUP_FILE%") do set "filesize=%%~zF"
if !filesize! leq 0 (
    echo [错误] 备份文件为空已删除无效文件
    del "%BACKUP_FILE%" 2>nul
    goto fail
)

echo.
echo [成功] 备份完成: %BACKUP_FILE%  大小 !filesize! 字节
echo.
echo 正在尝试 git add ...
git add "%BACKUP_FILE%" 2>nul
if errorlevel 1 (
    echo [提示] 未能 git add 请检查是否在 git 仓库内或 git 是否可用
) else (
    echo [成功] 已加入 git: %BACKUP_FILE%
)
goto done

:write_db_cnf
(
    echo [client]
    echo host=%DB_HOST%
    echo port=%DB_PORT%
    echo user=%DB_USER%
    echo password=%DB_PASS%
) > "%DB_CNF%"
exit /b 0

:fail
set "ERR=1"

:done
call :delete_db_cnf
if defined ERR (
    echo.
    echo 备份未成功完成
    echo 按任意键退出...
    pause >nul
    exit /b 1
)
exit /b 0

:delete_db_cnf
if exist "%DB_CNF%" del /f /q "%DB_CNF%" 2>nul
exit /b 0
