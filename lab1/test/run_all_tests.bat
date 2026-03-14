@echo off
echo Running all tests for tax calculator...
echo =========================================

REM Run compile script
echo Step 1: Compiling tests...
call compile_tests.bat
if %ERRORLEVEL% neq 0 (
    echo Compilation failed. Exiting.
    exit /b 1
)

echo.
echo =========================================
echo Step 2: Running tests...
call run_tests.bat
set TEST_RESULT=%ERRORLEVEL%

echo.
echo =========================================
echo Test Summary:
echo.

if %TEST_RESULT% equ 0 (
    echo All tests: PASSED
    echo.
    echo All tests passed successfully!
) else (
    echo All tests: FAILED
    echo.
    echo Some tests failed.
)

echo.
echo =========================================
echo Test execution completed.

exit /b %TEST_RESULT%