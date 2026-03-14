@echo off
echo Compiling tax calculator tests...
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

REM Check if JUnit libraries exist
if not exist "%JUNIT_API%" (
    echo Error: JUnit API library not found at %JUNIT_API%
    echo Run download_junit.bat first.
    exit /b 1
)

if not exist "%JUNIT_ENGINE%" (
    echo Error: JUnit Engine library not found at %JUNIT_ENGINE%
    echo Run download_junit.bat first.
    exit /b 1
)

if not exist "%JUNIT_PLATFORM%" (
    echo Error: JUnit Platform library not found at %JUNIT_PLATFORM%
    echo Run download_junit.bat first.
    exit /b 1
)

REM Create output directories
if not exist "%OUT_PROD%" mkdir "%OUT_PROD%"
if not exist "%OUT_TEST%" mkdir "%OUT_TEST%"

REM Compile source code
echo Compiling source code to %OUT_PROD%...
javac -encoding UTF-8 -d "%OUT_PROD%" -cp "%OUT_PROD%;%GSON_LIB%" -sourcepath "%SRC_DIR%" "%SRC_DIR%\model\entity\TaxConfig.java" "%SRC_DIR%\model\entity\TaxRule.java" "%SRC_DIR%\model\entity\TaxTable.java" "%SRC_DIR%\model\loader\TaxConfigLoader.java" "%SRC_DIR%\model\loader\DefaultTaxConfigLoader.java" "%SRC_DIR%\model\loader\JsonFileTaxConfigLoader.java" "%SRC_DIR%\model\loader\TaxConfigLoaderFactory.java" "%SRC_DIR%\service\TaxHandler.java" "%SRC_DIR%\service\BaseTaxHandler.java" "%SRC_DIR%\service\TaxContext.java" "%SRC_DIR%\service\TaxChain.java" "%SRC_DIR%\service\TaxCalculator.java" "%SRC_DIR%\view\TaxConsoleMenu.java" "%SRC_DIR%\Main.java"

if %ERRORLEVEL% neq 0 (
    echo Error compiling source code.
    exit /b 1
)

REM Compile test code
echo Compiling test code to %OUT_TEST%...
javac -encoding UTF-8 -d "%OUT_TEST%" -cp "%OUT_PROD%;%JUNIT_API%;%JUNIT_PLATFORM%;%GSON_LIB%" "%TEST_DIR%\BasicTaxTest.java" "%TEST_DIR%\RegressionTest.java" "%TEST_DIR%\JsonLoaderTest.java"

if %ERRORLEVEL% neq 0 (
    echo Error compiling test code.
    exit /b 1
)

echo.
echo Compilation successful!
echo Production classes: %OUT_PROD%
echo Test classes: %OUT_TEST%
echo.

REM List compiled test classes
echo Test classes compiled:
dir /b "%OUT_TEST%" | findstr /i ".class"