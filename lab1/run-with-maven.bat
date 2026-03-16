@echo off
REM ============================================================================
REM 个人所得税计算系统 - Maven 启动脚本 (Windows)
REM 该脚本用于有 Maven 环境的情况下启动项目和执行单元测试
REM ============================================================================

setlocal enabledelayedexpansion
chcp 65001 > nul

echo.
echo ============================================================================
echo 个人所得税计算系统 - Maven 版本启动脚本
echo ============================================================================
echo.

REM 检查是否存在 pom.xml
if not exist "pom.xml" (
    echo [错误] 找不到 pom.xml 文件，请确保在项目根目录下运行此脚本
    pause
    exit /b 1
)

REM 检查 Maven 是否安装
mvn --version > nul 2>&1
if errorlevel 1 (
    echo [错误] Maven 未找到或未在 PATH 中配置
    echo 请确保已安装 Maven 并配置了环境变量
    pause
    exit /b 1
)

echo [信息] Maven 版本检查成功
mvn --version
echo.

REM 菜单选项
:menu
echo ============================================================================
echo 请选择操作：
echo ============================================================================
echo 1. 清理项目并编译 (clean install)
echo 2. 执行所有单元测试 (test)
echo 3. 清理 + 编译 + 测试 (clean install)
echo 4. 打包项目 (package)
echo 5. 启动应用程序
echo 6. 清理构建目录 (clean)
echo 7. 退出
echo ============================================================================
set /p choice="请输入选项 [1-7]: "

if "%choice%"=="1" (
    echo.
    echo [执行] 清理项目并编译...
    mvn clean install -DskipTests
    if errorlevel 1 (
        echo [错误] 编译失败
        pause
        goto menu
    )
    echo [成功] 编译完成
    pause
    goto menu
) else if "%choice%"=="2" (
    echo.
    echo [执行] 执行所有单元测试...
    mvn test
    pause
    goto menu
) else if "%choice%"=="3" (
    echo.
    echo [执行] 清理、编译和测试...
    mvn clean install
    pause
    goto menu
) else if "%choice%"=="4" (
    echo.
    echo [执行] 打包项目...
    mvn clean package
    if errorlevel 1 (
        echo [错误] 打包失败
        pause
        goto menu
    )
    echo [成功] 打包完成，生成文件：target\tax-calculator.jar
    pause
    goto menu
) else if "%choice%"=="5" (
    echo.
    echo [执行] 启动应用程序...
    if not exist "target\tax-calculator.jar" (
        echo [信息] JAR 文件不存在，正在进行打包...
        mvn clean package -DskipTests
    )
    if exist "target\tax-calculator.jar" (
        java -cp target\tax-calculator.jar;target\classes;. Main
    ) else (
        echo [错误] JAR 文件生成失败
    )
    pause
    goto menu
) else if "%choice%"=="6" (
    echo.
    echo [执行] 清理构建目录...
    mvn clean
    echo [成功] 清理完成
    pause
    goto menu
) else if "%choice%"=="7" (
    echo.
    echo [信息] 退出脚本
    exit /b 0
) else (
    echo [错误] 无效的选项，请重新输入
    pause
    goto menu
)

