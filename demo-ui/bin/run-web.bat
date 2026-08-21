@echo off
echo.
echo [信息] 启动Web工程。
echo.

%~d0
cd %~dp0

cd ..
pnpm dev
