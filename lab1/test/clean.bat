@echo off
echo Cleaning test outputs...
echo.

set PROJECT_ROOT=..
set OUT_TEST=%PROJECT_ROOT%\out\test

if exist "%OUT_TEST%" (
    echo Removing test output directory: %OUT_TEST%
    rmdir /s /q "%OUT_TEST%"
    echo Test output directory removed.
) else (
    echo Test output directory does not exist: %OUT_TEST%
)

echo.
echo Clean completed.