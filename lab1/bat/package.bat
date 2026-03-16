@echo off
echo Packaging project...
cd ..
mvn clean package
cd bat
pause

