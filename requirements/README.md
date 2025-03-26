# Library Management System - Requirements

This document outlines the requirements and setup instructions for running the Library Management System on a new machine.

## System Requirements

1. **Java Development Kit (JDK)**
   - Version: Java 21 or higher
   - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://adoptium.net/)

2. **Maven**
   - Version: 3.8.0 or higher
   - Download: [Apache Maven](https://maven.apache.org/download.cgi)

3. **Database**
   - H2 Database (embedded, no separate installation required)
   - For production: Consider MySQL, PostgreSQL, or other relational databases

4. **IDE (Optional)**
   - Recommended: IntelliJ IDEA, Eclipse, VS Code with Java extensions

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone <repository-url>
   cd library-management
   ```

2. **Build the Application**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the Application**
   - API Endpoints: `http://localhost:8080/api/`
   - H2 Console: `http://localhost:8080/h2-console`
     - JDBC URL: `jdbc:h2:mem:librarydb`
     - Username: `sa`
     - Password: `password`

## Environment Variables (Optional)

For custom configurations, you can set the following environment variables:

```
SERVER_PORT=8080
SPRING_DATASOURCE_URL=jdbc:h2:mem:librarydb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=password
```

## Production Deployment

For production deployment, consider:

1. Using a persistent database (MySQL, PostgreSQL)
2. Configuring proper security measures
3. Setting up HTTPS
4. Implementing user authentication and authorization

## API Documentation

Refer to the main README.md file for detailed API documentation.

## Troubleshooting

1. **Port Conflict**: If port 8080 is already in use, change the port in `application.properties` or use the `SERVER_PORT` environment variable.

2. **Java Version**: Ensure you're using Java 21. Check with:
   ```bash
   java -version
   ```

3. **Maven Issues**: Ensure Maven is correctly installed and in your PATH:
   ```bash
   mvn -version
   ```

4. **Database Connection**: If using an external database, ensure connection details are correct in `application.properties`. 