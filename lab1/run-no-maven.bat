@echo off
setlocal enabledelayedexpansion

:menu
cls
echo.
echo ============================================================
echo Tax Calculator - Build Menu (without Maven)
echo ============================================================
echo.
echo 1. Run with compiled classes (java -cp)
echo 2. Run with JAR file (java -jar)
echo 3. Exit
echo.

set /p choice="Enter option [1-3]: "

if "%choice%"=="1" (
    cls
    echo Starting with compiled classes...
    if exist "target\classes" (
        java -cp "target\classes;." Main
    ) else (
        echo Classes not found. Please compile on a machine with Maven.
        pause
    )
    goto menu
) else if "%choice%"=="2" (
    cls
    echo Starting with JAR...
    if exist "target\tax-calculator.jar" (
        java -jar target\tax-calculator.jar
    ) else (
        echo JAR not found. Please build on a machine with Maven.
        pause
    )
    goto menu
) else if "%choice%"=="3" (
    exit /b 0
) else (
    cls
    echo Invalid option
    pause
    goto menu
)

