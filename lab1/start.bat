@echo off
if exist "target\tax-calculator.jar" (
    java -jar target\tax-calculator.jar
) else (
    echo JAR file not found
    echo Please run: build.bat and package.bat first
    pause
)

