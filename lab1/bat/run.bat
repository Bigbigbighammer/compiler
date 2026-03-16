@echo off
setlocal enabledelayedexpansion

:menu
cls
echo.
echo ============================================================
echo Tax Calculator - Build Menu (with Maven)
echo ============================================================
echo.
echo 1. Build (mvn clean install -DskipTests)
echo 2. Test (mvn test)
echo 3. Package (mvn clean package)
echo 4. Run (java -jar)
echo 5. Exit
echo.

set /p choice="Enter option [1-5]: "

if "%choice%"=="1" (
    cls
    echo Building...
    cd ..
    mvn clean install -DskipTests
    cd bat
    pause
    goto menu
) else if "%choice%"=="2" (
    cls
    echo Testing...
    cd ..
    mvn test
    cd bat
    pause
    goto menu
) else if "%choice%"=="3" (
    cls
    echo Packaging...
    cd ..
    mvn clean package
    cd bat
    pause
    goto menu
) else if "%choice%"=="4" (
    cls
    echo Starting...
    cd ..
    if exist "target\tax-calculator.jar" (
        java -jar target\tax-calculator.jar
    ) else (
        echo JAR not found. Building...
        mvn clean package -DskipTests
        if exist "target\tax-calculator.jar" (
            java -jar target\tax-calculator.jar
        )
    )
    cd bat
    pause
    goto menu
) else if "%choice%"=="5" (
    exit /b 0
) else (
    cls
    echo Invalid option
    pause
    goto menu
)

