REM UTF-8 with BOM
@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

REM ============================================================================
REM qualitest-demo 加载指定测试场景 SQL
REM 用法: load_scenario.bat [场景文件相对 seed 目录]
REM 示例: load_scenario.bat scenarios\success\S03_paid_pending_ship.sql
REM       load_scenario.bat scenarios\fail\F07_balance_insufficient_order.sql
REM 建议: 先执行 reset_test_data.bat 再加载场景
REM ============================================================================

cd /d "%~dp0"

if "%~1"=="" (
    echo 用法: load_scenario.bat ^<场景SQL路径^>
    echo.
    echo 成功场景:
    dir /b scenarios\success\*.sql 2>nul
    echo.
    echo 失败场景:
    dir /b scenarios\fail\*.sql 2>nul
    goto fail
)

set "SCENARIO=%~1"
if not exist "%SCENARIO%" (
    echo [错误] 文件不存在: %SCENARIO%
    goto fail
)

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

call :write_db_cnf

echo 正在加载场景: %SCENARIO%
"%MYSQL%" --defaults-extra-file="%DB_CNF%" %DB_NAME% < "%SCENARIO%"
if errorlevel 1 (
    echo [错误] 场景加载失败
    goto fail
)

echo [成功] 场景已加载
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
if defined ERR exit /b 1
exit /b 0

:delete_db_cnf
if exist "%DB_CNF%" del /f /q "%DB_CNF%" 2>nul
exit /b 0
