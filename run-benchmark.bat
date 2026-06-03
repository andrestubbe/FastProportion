@echo off
chcp 65001 >nul
setlocal
cd /d "%~dp0"

echo ⚡ FastProportion JMH Benchmark (v0.1.0)
echo 🚀 Building Core Library...
call mvn -q clean install -DskipTests

echo 🚀 Building Benchmark Module...
cd examples\Benchmark
call mvn -q clean package

echo 🚀 Launching Benchmark...
java -jar target\benchmarks.jar

pause
