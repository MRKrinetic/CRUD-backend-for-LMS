@echo off
echo Library Management System - Setup Script
echo =======================================

echo Checking Java installation...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo Java is not installed or not in PATH.
    echo Please install Java 21 or higher from https://adoptium.net/
    exit /b 1
)

echo Checking Maven installation...
mvn -version
if %ERRORLEVEL% NEQ 0 (
    echo Maven is not installed or not in PATH.
    echo Please install Maven 3.8.0 or higher from https://maven.apache.org/download.cgi
    exit /b 1
)

echo Building the application...
cd ..
mvn clean install
if %ERRORLEVEL% NEQ 0 (
    echo Build failed. Please check the error messages above.
    exit /b 1
)

echo.
echo Setup completed successfully!
echo.
echo To run the application, use: mvn spring-boot:run
echo The application will be available at: http://localhost:8080/api/
echo H2 Console will be available at: http://localhost:8080/h2-console
echo.
echo Thank you for using Library Management System! 