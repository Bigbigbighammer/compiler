@echo off
REM 税收计算器菜单脚本
REM 提供构建、测试、打包和运行功能

setlocal enabledelayedexpansion

:menu
cls
echo.
echo ============================================================
echo           Tax Calculator - Build Menu ^(with Maven^)
echo ============================================================
echo.
echo 1. Build ^(mvn clean install -DskipTests^)
echo 2. Test ^(mvn test^)
echo 3. Package ^(mvn clean package^)
echo 4. Run ^(java -jar^)
echo 5. Exit
echo.

set /p choice="Enter option [1-5]: "

if "%choice%"=="1" (
    cls
    echo Building...
    pushd .. 2>nul || (
        echo Error: Cannot change directory. Please ensure run.bat is in the bat folder.
        pause
        goto menu
    )
    call mvn clean install -DskipTests
    if errorlevel 1 (
        echo.
        echo ERROR: Build failed!
    ) else (
        echo.
        echo Build completed successfully!
    )
    popd
    echo.
    pause
    goto menu
) else if "%choice%"=="2" (
    cls
    echo Testing...
    pushd .. 2>nul || (
        echo Error: Cannot change directory.
        pause
        goto menu
    )
    call mvn test
    if errorlevel 1 (
        echo.
        echo ERROR: Tests failed!
    ) else (
        echo.
        echo Tests completed successfully!
    )
    popd
    echo.
    pause
    goto menu
) else if "%choice%"=="3" (
    cls
    echo Packaging...
    pushd .. 2>nul || (
        echo Error: Cannot change directory.
        pause
        goto menu
    )
    call mvn clean package
    if errorlevel 1 (
        echo.
        echo ERROR: Packaging failed!
    ) else (
        echo.
        echo Packaging completed successfully!
    )
    popd
    echo.
    pause
    goto menu
) else if "%choice%"=="4" (
    cls
    echo Starting...
    pushd .. 2>nul || (
        echo Error: Cannot change directory.
        pause
        goto menu
    )
    REM 优先尝试运行包含依赖的 Fat JAR
    if exist "target\tax-calculator-all.jar" (
        echo Running jar file ^(with dependencies^)...
        java -jar target\tax-calculator-all.jar
        if errorlevel 1 (
            echo.
            echo ERROR: Program failed!
            pause
        )
    ) else if exist "target\tax-calculator.jar" (
        echo Running jar file...
        java -jar target\tax-calculator.jar
        if errorlevel 1 (
            echo.
            echo ERROR: Program failed!
            pause
        )
    ) else (
        echo JAR not found. Building...
        call mvn clean package -DskipTests
        if errorlevel 1 (
            echo ERROR: Packaging failed!
        ) else if exist "target\tax-calculator-all.jar" (
            echo Running jar file ^(with dependencies^)...
            java -jar target\tax-calculator-all.jar
            if errorlevel 1 (
                echo.
                echo ERROR: Program failed!
                pause
            )
        ) else if exist "target\tax-calculator.jar" (
            echo Running jar file...
            java -jar target\tax-calculator.jar
            if errorlevel 1 (
                echo.
                echo ERROR: Program failed!
                pause
            )
        ) else (
            echo ERROR: JAR file still not found after building!
            pause
        )
    )
    popd
    echo.
    pause
    goto menu
) else if "%choice%"=="5" (
    echo Exiting...
    endlocal
    exit /b 0
) else (
    cls
    echo Invalid option!
    echo.
    pause
    goto menu
)

REM 脚本结束时清理
endlocal
exit /b 0

