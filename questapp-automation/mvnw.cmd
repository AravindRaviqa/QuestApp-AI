@echo off
REM Maven Wrapper for QuestApp Automation
REM This script provides easy access to Maven commands

set MAVEN_HOME=%~dp0apache-maven-3.9.6
set PATH=%MAVEN_HOME%\bin;%PATH%

if "%1"=="" (
    echo QuestApp Automation - Maven Wrapper
    echo.
    echo Usage:
    echo   mvnw.cmd test                    - Run all tests
    echo   mvnw.cmd clean test              - Clean and run tests
    echo   mvnw.cmd test -Dtest=LoginTest   - Run specific test
    echo   mvnw.cmd test -Pchrome-headless  - Run with headless Chrome
    echo   mvnw.cmd test -Pparallel         - Run tests in parallel
    echo   mvnw.cmd clean                   - Clean project
    echo   mvnw.cmd compile                 - Compile project
    echo.
    echo For more options, see README.md
    exit /b 1
)

echo Running: mvn %*
call mvn %* 