# Script Usage Guide

## Overview

Two sets of scripts are provided:
- **With Maven**: Full build, test, and package support
- **Without Maven**: Only run pre-built JAR/classes

## Scripts for Maven Environment

### run.bat (Recommended - Full Menu)
Interactive menu with all options:
1. Build (mvn clean install -DskipTests)
2. Test (mvn test)
3. Package (mvn clean package)
4. Run (java -jar)
5. Exit

```bash
run.bat
```

### Quick Scripts
For quick operations:

| Script | Command | Purpose |
|--------|---------|---------|
| build.bat | mvn clean install -DskipTests | Compile project |
| test.bat | mvn test | Run unit tests |
| package.bat | mvn clean package | Create JAR file |
| start.bat | java -jar ... | Start application |

Example usage:
```bash
build.bat
test.bat
package.bat
start.bat
```

## Scripts for Non-Maven Environment

### run-no-maven.bat
Menu for running pre-built application:
1. Run with compiled classes (java -cp)
2. Run with JAR file (java -jar)
3. Exit

```bash
run-no-maven.bat
```

**Requirements**: 
- Project must be compiled on a machine with Maven first
- Copy entire project folder to non-Maven machine
- Run this script to execute the application

## Quick Start

### With Maven (Recommended)
```bash
run.bat          # Opens interactive menu
```

Or use quick scripts:
```bash
build.bat        # Compile
package.bat      # Create JAR
start.bat        # Run
```

### Without Maven
```bash
run-no-maven.bat # Opens menu for running pre-built app
```

## Important Notes

1. **First Time Setup**
   - Run `build.bat` to compile
   - Run `package.bat` to create JAR

2. **For Non-Maven Users**
   - Project must be compiled on Maven machine first
   - Then copy `target\classes` or `target\tax-calculator.jar` to your machine

3. **All Scripts Support Looping**
   - After operation completes, press any key to return to menu
   - Can perform multiple operations without restarting script

## Troubleshooting

**Maven not found**
- Install Maven
- Add to PATH environment variable
- Restart terminal/IDE

**JAR not found**
- Run `build.bat` first
- Then run `package.bat`
- Then run `start.bat`

**Java not found**
- Install JDK 8+
- Add JAVA_HOME to environment variables
- Restart terminal/IDE

---

**Version**: 3.0.0  
**Last Updated**: 2026-03-16  
**Status**: ✅ All scripts organized and working

