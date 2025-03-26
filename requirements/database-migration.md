# Database Migration Guide

This guide provides instructions for migrating from the default H2 in-memory database to a production database.

## Supported Databases

The Library Management System supports the following databases:

1. H2 (default, in-memory)
2. MySQL
3. PostgreSQL
4. Oracle
5. Microsoft SQL Server

## Migration Steps

### 1. Add Database Dependency

Add the appropriate database dependency to your `pom.xml`:

#### For MySQL:
```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
```

#### For PostgreSQL:
```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

### 2. Update Application Properties

Modify `src/main/resources/application.properties` to use your production database:

#### For MySQL:
```properties
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/librarydb?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
```

#### For PostgreSQL:
```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/librarydb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### 3. Database Initialization

For a new database, set `spring.jpa.hibernate.ddl-auto=create` the first time you run the application to create the schema. After the first run, change it to `update` to preserve data.

### 4. Data Migration

If you need to migrate data from H2 to your production database:

1. Export data from H2:
   - Access H2 console at `http://localhost:8080/h2-console`
   - Run SQL queries to export data (e.g., `SELECT * FROM BOOKS`)
   - Save results as CSV or SQL script

2. Import data to production database:
   - Use database-specific tools (MySQL Workbench, pgAdmin, etc.)
   - Import the CSV files or run SQL scripts

### 5. Connection Pooling

For production, configure connection pooling:

```properties
# HikariCP settings
spring.datasource.hikari.connection-timeout=20000
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
spring.datasource.hikari.idle-timeout=300000
spring.datasource.hikari.max-lifetime=1200000
```

### 6. Backup Strategy

Implement a regular backup strategy for your production database:

1. Set up automated backups
2. Test restoration procedures
3. Store backups securely in multiple locations

## Troubleshooting

1. **Connection Issues**: Ensure database server is running and accessible from your application server
2. **Authentication Issues**: Verify username and password
3. **Schema Issues**: Check if tables exist and have the correct structure
4. **Performance Issues**: Monitor query performance and optimize as needed 