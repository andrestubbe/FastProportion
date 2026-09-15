@echo off
setlocal enabledelayedexpansion
chcp 65001 >nul
cd /d "%~dp0"

echo ================================================================================
echo   FastProportion ? Official JMH Performance Benchmark
echo ================================================================================
echo.

echo ? Building Main Project (FastProportion)...
call mvn install -DskipTests -q
if %ERRORLEVEL% NEQ 0 ( echo ? Main build failed. & pause & exit /b %ERRORLEVEL% )

echo ?? Building Benchmark Uber-JAR...
cd examples\Benchmark
call mvn clean package -DskipTests -q
if %ERRORLEVEL% NEQ 0 ( echo ? Benchmark build failed. & pause & exit /b %ERRORLEVEL% )

echo ?? Running Official JMH Benchmarks for FastProportion...
java -Djmh.ignoreLock=true -jar target\benchmarks.jar -f 1 -wi 2 -i 3 -tu ms -bm thrpt

cd ..\..
echo.
pause
