@echo off
echo Running tests...
echo.

REM Set project directories
set PROJECT_ROOT=..
set SRC_DIR=%PROJECT_ROOT%\src
set TEST_DIR=%PROJECT_ROOT%\test
set LIB_DIR=%TEST_DIR%\lib
set GSON_LIB=%PROJECT_ROOT%\lib\gson-2.10.1.jar
set OUT_PROD=%PROJECT_ROOT%\out\production\lab1
set OUT_TEST=%PROJECT_ROOT%\out\test\lab1

REM JUnit libraries
set JUNIT_API=%LIB_DIR%\junit-jupiter-api-5.9.2.jar
set JUNIT_ENGINE=%LIB_DIR%\junit-jupiter-engine-5.9.2.jar
set JUNIT_PLATFORM=%LIB_DIR%\junit-platform-console-standalone-1.9.2.jar

REM Check if tests are compiled
if not exist "%OUT_TEST%" (
    echo Error: Test classes not found. Run compile_tests.bat first.
    exit /b 1
)

echo Running tests...
echo.

REM Run tests using JUnit Platform Console Standalone
java -jar "%JUNIT_PLATFORM%" --class-path "%OUT_PROD%;%OUT_TEST%;%JUNIT_API%;%JUNIT_ENGINE%" --scan-class-path

set TEST_RESULT=%ERRORLEVEL%

echo.
if %TEST_RESULT% equ 0 (
    echo All tests passed!
) else (
    echo Some tests failed.
)

exit /b %TEST_RESULT%