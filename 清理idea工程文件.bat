@echo off
rem create by NettQun
rem 这里写你的iml
set REPOSITORY_PATH=E:\project\hoioy\diamond
rem 正在搜索...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*iml*"') do (
    del /s /q %%i
)
rem 搜索完毕
pause