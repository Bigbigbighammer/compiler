@echo off
setlocal enabledelayedexpansion

:: =================== 项目配置 (可根据实际情况修改) ====================
:: !! 关键: 如果你的主类不是 "Main.java", 请在这里修改
set MAIN_CLASS=Main

:: 定义项目结构路径 (脚本会自动计算)
set "PROJECT_ROOT=%~dp0.."
set "SRC_DIR=%PROJECT_ROOT%\src\main\java"
set "TEST_DIR=%PROJECT_ROOT%\src\test\java"
set "OUT_DIR=%PROJECT_ROOT%\target"
set "LIB_DIR=%PROJECT_ROOT%\lib"
set "JAR_NAME=tax-calculator.jar"
set "JUNIT_JAR_NAME=junit-platform-console-standalone-1.9.3.jar"
:: =======================================================================


:menu
cls
echo.
echo ============================================================
echo      Tax Calculator - Build & Run Script (No Maven)
echo ============================================================
echo.
echo  1. Compile and Run Unit Tests
echo  2. Compile and Run Application (from .class files)
echo  3. Build and Run Application (from .jar file)
echo  4. Exit
echo.

set /p choice="Enter option [1-4]: "

if "%choice%"=="1" goto :run_tests
if "%choice%"=="2" goto :run_from_classes
if "%choice%"=="3" goto :run_from_jar
if "%choice%"=="4" exit /b 0

cls
echo Invalid option
pause
goto menu


:run_tests
cls
echo ===================== Running Unit Tests =======================
echo.

:: 检查 JUnit jar 包是否存在
if not exist "%LIB_DIR%\%JUNIT_JAR_NAME%" (
    echo [ERROR] JUnit JAR not found!
    echo Please ensure '%JUNIT_JAR_NAME%' is in the '%LIB_DIR%' directory.
    pause
    goto :menu
)

:: 1. 清理并创建输出目录
echo [1/4] Cleaning output directory...
if exist "%OUT_DIR%" rd /s /q "%OUT_DIR%"
mkdir "%OUT_DIR%\classes"
mkdir "%OUT_DIR%\test-classes"

:: 2. 编译业务代码
echo [2/4] Compiling application source code...
dir /s /B "%SRC_DIR%\*.java" > sources_main.txt
javac -encoding UTF-8 -d "%OUT_DIR%\classes" -cp "%LIB_DIR%\*" @sources_main.txt
if %errorlevel% neq 0 (
    echo [ERROR] Application source code compilation failed.
    del sources_main.txt
    pause
    goto :menu
)
del sources_main.txt

:: 3. 编译测试代码
echo [3/4] Compiling test source code...
dir /s /B "%TEST_DIR%\*.java" > sources_test.txt
javac -encoding UTF-8 -d "%OUT_DIR%\test-classes" -cp "%OUT_DIR%\classes;%LIB_DIR%\*" @sources_test.txt
if %errorlevel% neq 0 (
    echo [ERROR] Test source code compilation failed.
    del sources_test.txt
    pause
    goto :menu
)
del sources_test.txt
echo Compilation successful.

:: 4. 运行测试
echo [4/4] Executing tests...
echo --------------------------------------------------------------
java -jar "%LIB_DIR%\%JUNIT_JAR_NAME%" --class-path "%OUT_DIR%\test-classes;%OUT_DIR%\classes;%LIB_DIR%\*" --scan-class-path
echo --------------------------------------------------------------
echo.
echo Tests finished. Press any key to return to the menu.
pause
goto menu


:run_from_classes
cls
echo ======== Compile and Run Application (from .class files) =======
echo.

:: 1. 编译业务代码
echo [1/2] Compiling application source code...
if not exist "%OUT_DIR%\classes" mkdir "%OUT_DIR%\classes"
dir /s /B "%SRC_DIR%\*.java" > sources_main.txt
javac -encoding UTF-8 -d "%OUT_DIR%\classes" -cp "%LIB_DIR%\*" @sources_main.txt
if %errorlevel% neq 0 (
    echo [ERROR] Application source code compilation failed.
    del sources_main.txt
    pause
    goto :menu
)
del sources_main.txt
echo Compilation successful.

:: 2. 运行主程序
echo.
echo [2/2] Running application...
echo --------------------------------------------------------------
java -cp "%OUT_DIR%\classes;%LIB_DIR%\*" %MAIN_CLASS%
echo --------------------------------------------------------------
echo.
echo Application finished. Press any key to return to the menu.
pause
goto menu


:run_from_jar
cls
echo ========= Build and Run Application (from .jar file) ===========
echo.

:: 1. 编译业务代码 (同上)
echo [1/3] Compiling application source code...
if not exist "%OUT_DIR%\classes" mkdir "%OUT_DIR%\classes"
dir /s /B "%SRC_DIR%\*.java" > sources_main.txt
javac -encoding UTF-8 -d "%OUT_DIR%\classes" -cp "%LIB_DIR%\*" @sources_main.txt
if %errorlevel% neq 0 (
    echo [ERROR] Application source code compilation failed.
    del sources_main.txt
    pause
    goto :menu
)
del sources_main.txt
echo Compilation successful.

:: 2. 打包成可执行 JAR
echo [2/3] Building executable JAR file...
jar cfe "%OUT_DIR%\%JAR_NAME%" %MAIN_CLASS% -C "%OUT_DIR%\classes" .
if %errorlevel% neq 0 (
    echo [ERROR] Failed to build JAR file.
    pause
    goto :menu
)
echo JAR file created successfully at '%OUT_DIR%\%JAR_NAME%'

:: 3. 运行 JAR
echo.
echo [3/3] Running application from JAR...
echo --------------------------------------------------------------
java -jar "%OUT_DIR%\%JAR_NAME%"
echo --------------------------------------------------------------
echo.
echo Application finished. Press any key to return to the menu.
pause
goto menu