# Library Management System

A Spring Boot backend application for managing a library, including book management, user management, and borrowing functionality.

## Features

- **Book Management**: Add, update, delete, and search books
- **User Management**: Add, update, delete, and search users (students, faculty, etc.)
- **Borrowing System**: Borrow and return books, track due dates
- **Fine Management**: Calculate and manage fines for overdue books
- **User Statistics**: Track fine pending and issued books for each user

## Technologies Used

- Java 21
- Spring Boot 3.2.3
- Spring Data JPA
- H2 Database (for development)
- Maven
- Lombok

## Quick Start

### For Windows users:
```
requirements/setup.bat
```

### For Linux/Mac users:
```
chmod +x requirements/setup.sh && ./requirements/setup.sh
```

### For Docker users:
```
cd requirements && docker-compose up -d
```

## API Endpoints

### Book Endpoints

- `GET /api/books` - Get all books
- `GET /api/books/{id}` - Get book by ID
- `GET /api/books/isbn/{isbn}` - Get book by ISBN
- `GET /api/books/search?keyword={keyword}` - Search books by keyword
- `GET /api/books/category/{category}` - Get books by category
- `GET /api/books/author/{author}` - Get books by author
- `GET /api/books/available` - Get all available books
- `POST /api/books` - Add a new book
- `PUT /api/books/{id}` - Update a book
- `DELETE /api/books/{id}` - Delete a book

            # Get all books
            Invoke-RestMethod -Uri "http://localhost:8081/api/books" -Method Get

            # Get book by ID
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/1" -Method Get

            # Get book by ISBN
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/isbn/9780201616224" -Method Get

            # Search books by keyword
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/search?keyword=Programming" -Method Get

            # Get books by category
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/category/Fiction" -Method Get

            # Get books by author
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/author/Robert%20Martin" -Method Get

            # Get all available books
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/available" -Method Get

            # Add a new book (POST)
            Invoke-RestMethod -Uri "http://localhost:8081/api/books" -Method Post -Body (@{title="Clean Code"; author="Robert Martin"; isbn="9780132350884"; category="Programming"} | ConvertTo-Json) -ContentType "application/json"

            # Update a book (PUT)
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/1" -Method Put -Body (@{title="Clean Code Updated"; author="Robert Martin"; isbn="9780132350884"; category="Programming"} | ConvertTo-Json) -ContentType "application/json"

            # Delete a book
            Invoke-RestMethod -Uri "http://localhost:8081/api/books/1" -Method Delete


### User Endpoints

- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users/student/{studentId}` - Get user by student ID
- `GET /api/users/search?keyword={keyword}` - Search users by keyword
- `GET /api/users/type/{userType}` - Get users by type (STUDENT, FACULTY, ADMIN, LIBRARIAN)
- `GET /api/users/{id}/borrowed-count` - Get count of books currently borrowed by user
- `GET /api/users/{id}/can-borrow` - Check if user can borrow more books
- `GET /api/users/{id}/fine-pending` - Get total fine pending for a user
- `GET /api/users/{id}/issued-books` - Get count of currently issued books for a user
- `POST /api/users` - Add a new user
- `PUT /api/users/{id}` - Update a user
- `PUT /api/users/{id}/pay-all-fines` - Pay all pending fines for a user
- `DELETE /api/users/{id}` - Delete a user
- `PUT /api/users/{id}/activate` - Activate a user
- `PUT /api/users/{id}/deactivate` - Deactivate a user

            # Get all users
            Invoke-RestMethod -Uri "http://localhost:8081/api/users" -Method Get

            # Get user by ID
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1" -Method Get

            # Get user by email
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/email/user@example.com" -Method Get

            # Get user by student ID
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/student/123456" -Method Get

            # Search users by keyword
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/search?keyword=John" -Method Get

            # Get users by type
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/type/STUDENT" -Method Get

            # Get count of books currently borrowed by user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/borrowed-count" -Method Get

            # Check if user can borrow more books
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/can-borrow" -Method Get

            # Get total fine pending for a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/fine-pending" -Method Get

            # Get issued books count for a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/issued-books" -Method Get

            # Add a new user (POST)
            Invoke-RestMethod -Uri "http://localhost:8081/api/users" -Method Post -Body (@{name="John Doe"; email="john@example.com"; studentId="123456"; userType="STUDENT"} | ConvertTo-Json) -ContentType "application/json"

            # Update a user (PUT)
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1" -Method Put -Body (@{name="John Updated"; email="john@example.com"; studentId="123456"; userType="STUDENT"} | ConvertTo-Json) -ContentType "application/json"

            # Pay all pending fines for a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/pay-all-fines" -Method Put

            # Activate a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/activate" -Method Put

            # Deactivate a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1/deactivate" -Method Put

            # Delete a user
            Invoke-RestMethod -Uri "http://localhost:8081/api/users/1" -Method Delete


### Borrowing Endpoints

- `GET /api/borrowings` - Get all borrowings
- `GET /api/borrowings/{id}` - Get borrowing by ID
- `GET /api/borrowings/user/{userId}` - Get all borrowings by user
- `GET /api/borrowings/user/{userId}/current` - Get current borrowings by user
- `GET /api/borrowings/overdue` - Get all overdue books
- `GET /api/borrowings/status/{status}` - Get borrowings by status (BORROWED, RETURNED, OVERDUE, LOST)
- `GET /api/borrowings/{id}/fine` - Calculate fine for a borrowing
- `POST /api/borrowings/borrow` - Borrow a book
- `PUT /api/borrowings/{id}/return` - Return a book
- `PUT /api/borrowings/{id}/pay-fine` - Pay fine for a borrowing
- `PUT /api/borrowings/{id}/status` - Update borrowing status
- `DELETE /api/borrowings/{id}` - Delete a borrowing

                # Get all borrowings
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings" -Method Get

                # Get borrowing by ID
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1" -Method Get

                # Get all borrowings by user
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/user/1" -Method Get

                # Get current borrowings by user
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/user/1/current" -Method Get

                # Get all overdue books
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/overdue" -Method Get

                # Get borrowings by status
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/status/BORROWED" -Method Get

                # Calculate fine for a borrowing
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1/fine" -Method Get

                # Borrow a book (POST)
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/borrow" -Method Post -Body (@{userId=1; bookId=2} | ConvertTo-Json) -ContentType "application/json"

                # Return a book (PUT)
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1/return" -Method Put

                # Pay fine for a borrowing
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1/pay-fine" -Method Put

                # Update borrowing status
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1/status" -Method Put -Body (@{status="RETURNED"} | ConvertTo-Json) -ContentType "application/json"

                # Delete a borrowing
                Invoke-RestMethod -Uri "http://localhost:8081/api/borrowings/1" -Method Delete


## Setup and Installation

1. Clone the repository
2. Make sure you have Java 21 and Maven installed
3. Run `mvn clean install` to build the project
4. Run `mvn spring-boot:run` to start the application
5. The application will be available at `http://localhost:8080`
6. H2 Console is available at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:librarydb`, Username: `sa`, Password: `password`)

## Database Schema

- **books**: Stores book information (title, author, ISBN, etc.)
- **users**: Stores user information (name, email, student ID, fine pending, issued books, etc.)
- **borrowings**: Tracks book borrowings (user, book, borrow date, due date, etc.)

## Future Enhancements

- Authentication and Authorization
- Email notifications for due dates
- Reservation system for books
- Reports and statistics
- Integration with external book APIs 