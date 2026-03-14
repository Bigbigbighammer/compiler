@echo off
echo Downloading JUnit 5 libraries...
echo.

REM Create lib directory if it doesn't exist
if not exist "lib" mkdir "lib"

REM JUnit Jupiter API 5.9.2
echo Downloading junit-jupiter-api-5.9.2.jar...
curl -L -o "lib\junit-jupiter-api-5.9.2.jar" "https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-api/5.9.2/junit-jupiter-api-5.9.2.jar"
if %ERRORLEVEL% neq 0 (
    echo Failed to download junit-jupiter-api-5.9.2.jar
    exit /b 1
)

REM JUnit Jupiter Engine 5.9.2
echo Downloading junit-jupiter-engine-5.9.2.jar...
curl -L -o "lib\junit-jupiter-engine-5.9.2.jar" "https://repo1.maven.org/maven2/org/junit/jupiter/junit-jupiter-engine/5.9.2/junit-jupiter-engine-5.9.2.jar"
if %ERRORLEVEL% neq 0 (
    echo Failed to download junit-jupiter-engine-5.9.2.jar
    exit /b 1
)

REM JUnit Platform Console Standalone 1.9.2
echo Downloading junit-platform-console-standalone-1.9.2.jar...
curl -L -o "lib\junit-platform-console-standalone-1.9.2.jar" "https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.2/junit-platform-console-standalone-1.9.2.jar"
if %ERRORLEVEL% neq 0 (
    echo Failed to download junit-platform-console-standalone-1.9.2.jar
    exit /b 1
)

echo.
echo JUnit libraries downloaded successfully to lib\
echo Files:
dir /b "lib\"
echo.
echo You can now compile and run tests using the other scripts.