REM UTF-8 with BOM
@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

REM ============================================================================
REM qualitest 数据库还原脚本
REM 功能 - 列出当前目录下 qualitest_*.sql 备份选择后还原到 MySQL
REM 用法 - 双击运行按提示输入序号直接回车则使用默认项 1 即最新备份
REM 注意 - 还原会覆盖目标库中现有数据操作前请确认
REM 编码 - 本文件为 UTF-8 BOM 换行符须为 CRLF
REM ============================================================================

REM 切换到脚本所在目录确保能找到同目录下的备份文件
cd /d "%~dp0"

REM ---------- 数据库连接配置须与 backup_db.bat 保持一致 ----------
set "DB_HOST=127.0.0.1"
set "DB_PORT=3306"
set "DB_USER=root"
set "DB_PASS=123456"
set "DB_NAME=qualitest-demo"

REM 临时连接配置文件 使用后自动删除 避免命令行 -p 触发 insecure 警告
set "DB_CNF=%~dp0_db_session.cnf"

REM ---------- MySQL 客户端路径按实际安装路径修改 ----------
set "MYSQL_BIN=C:\Program Files\MySQL\MySQL Server 8.0\bin"
set "MYSQL=%MYSQL_BIN%\mysql.exe"

if not exist "%MYSQL%" (
    echo [错误] 未找到 mysql
    echo   路径: %MYSQL%
    echo   请修改脚本顶部的 MYSQL_BIN
    goto fail
)

REM 扫描备份文件按修改时间倒序排列 dir /o-d 使最新备份编号为 1
set "file_count=0"
echo 正在扫描备份文件 ...
echo.

for /f "delims=" %%F in ('dir /b /o-d "%DB_NAME%_*.sql" 2^>nul') do (
    set /a file_count+=1
    set "file_!file_count!=%%F"
    echo   [!file_count!] %%F
)

if !file_count! equ 0 (
    echo [错误] 未找到匹配的备份文件: %DB_NAME%_*.sql
    goto fail
)

REM 交互选择备份直接回车则使用默认项 1
echo.
echo 默认选择: [1] !file_1!
echo.
set "default_choice=1"

:select_file
set /p "choice=请输入序号 1-!file_count! 默认 !default_choice!: "
if not defined choice set "choice=!default_choice!"
echo !choice!| findstr /r "^[1-9][0-9]*$" >nul || goto select_file
if !choice! lss 1 goto select_file
if !choice! gtr !file_count! goto select_file

set "selected_file=!file_%choice%!"
for %%F in ("!selected_file!") do set /a "size_kb=%%~zF/1024"

echo.
echo 已选择: !selected_file!  约 !size_kb! KB
echo.
echo 正在还原到数据库 %DB_NAME% ...
echo.

call :write_db_cnf

REM 从 SQL 文件导入错误信息直接显示在控制台不写入临时日志文件
"%MYSQL%" --defaults-extra-file="%DB_CNF%" %DB_NAME% < "!selected_file!"
if errorlevel 1 (
    echo.
    echo [错误] 还原失败请根据上方 mysql 输出排查原因
    goto fail
)

echo.
echo [成功] 还原完成来源: !selected_file!
echo.
echo 正在校验 ...
"%MYSQL%" --defaults-extra-file="%DB_CNF%" -N -e "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='%DB_NAME%';"
if errorlevel 1 (
    echo [警告] 校验查询失败请手动检查数据库
) else (
    echo [成功] 校验通过数据库 %DB_NAME% 可正常访问
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
    echo 还原未成功完成
    echo 按任意键退出...
    pause >nul
    exit /b 1
)
exit /b 0

:delete_db_cnf
if exist "%DB_CNF%" del /f /q "%DB_CNF%" 2>nul
exit /b 0
