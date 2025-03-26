#!/bin/bash

echo "Library Management System - Setup Script"
echo "========================================"

echo "Checking Java installation..."
if ! java -version &> /dev/null; then
    echo "Java is not installed or not in PATH."
    echo "Please install Java 21 or higher from https://adoptium.net/"
    exit 1
fi

echo "Checking Maven installation..."
if ! mvn -version &> /dev/null; then
    echo "Maven is not installed or not in PATH."
    echo "Please install Maven 3.8.0 or higher from https://maven.apache.org/download.cgi"
    exit 1
fi

echo "Building the application..."
cd ..
if ! mvn clean install; then
    echo "Build failed. Please check the error messages above."
    exit 1
fi

echo ""
echo "Setup completed successfully!"
echo ""
echo "To run the application, use: mvn spring-boot:run"
echo "The application will be available at: http://localhost:8080/api/"
echo "H2 Console will be available at: http://localhost:8080/h2-console"
echo ""
echo "Thank you for using Library Management System!" 