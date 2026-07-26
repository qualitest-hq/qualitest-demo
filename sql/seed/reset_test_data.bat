REM UTF-8 with BOM
@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

REM ============================================================================
REM qualitest-demo 业务测试数据重置
REM 功能 - 清空 13 张业务表并加载标准 seed（01 + 02，不含 03 购物车）
REM 用法 - 双击运行或在命令行执行 reset_test_data.bat
REM 详见项目根目录 README.md「测试数据 Seed 脚本」
REM ============================================================================

cd /d "%~dp0"

set "DB_HOST=127.0.0.1"
set "DB_PORT=3306"
set "DB_USER=root"
set "DB_PASS=123456"
set "DB_NAME=qualitest-demo"

set "DB_CNF=%~dp0_db_session.cnf"
set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server 8.0\bin"
set "MYSQL=%MYSQL_BIN%\mysql.exe"

if not exist "%MYSQL%" (
    echo [错误] 未找到 mysql: %MYSQL%
    goto fail
)

echo 正在重置 %DB_NAME% 业务测试数据 ...
echo.

call :write_db_cnf

for %%F in (00_truncate_business.sql 01_base_seed.sql 02_account_seed.sql) do (
    echo 执行 %%F ...
    "%MYSQL%" --defaults-extra-file="%DB_CNF%" %DB_NAME% < "%%F"
    if errorlevel 1 (
        echo [错误] 执行 %%F 失败
        goto fail
    )
)

echo.
echo [成功] 业务数据已重置为基线 seed
echo   可选: mysql ... ^< 03_trade_seed.sql          （预置购物车）
echo   可选: mysql ... ^< scenarios/success/*.sql    （成功场景）
echo   可选: mysql ... ^< scenarios/fail/*.sql      （失败场景）
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
    echo 重置未成功完成
    pause >nul
    exit /b 1
)
exit /b 0

:delete_db_cnf
if exist "%DB_CNF%" del /f /q "%DB_CNF%" 2>nul
exit /b 0
