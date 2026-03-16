@echo off
echo Building project...
cd ..
mvn clean install -DskipTests
cd bat
pause

