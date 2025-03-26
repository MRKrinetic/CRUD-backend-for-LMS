package com.edulib.library.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.edulib.library.model.Book;
import com.edulib.library.model.Borrowing;
import com.edulib.library.model.User;
import com.edulib.library.service.BookService;
import com.edulib.library.service.BorrowingService;
import com.edulib.library.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookService bookService;
    private final UserService userService;
    private final BorrowingService borrowingService;

    @Override
    public void run(String... args) {
        // Create sample books
        createSampleBooks();
        
        // Create sample users
        createSampleUsers();
        
        // Create sample borrowings
        createSampleBorrowings();
        
        // Update user statistics
        updateUserStatistics();
    }
    
    private void createSampleBooks() {
        // Book 1
        Book book1 = new Book();
        book1.setTitle("Clean Code: A Handbook of Agile Software Craftsmanship");
        book1.setAuthor("Robert C. Martin");
        book1.setIsbn("9780132350884");
        book1.setPublicationDate(LocalDate.of(2008, 8, 1));
        book1.setPublisher("Prentice Hall");
        book1.setQuantity(5);
        book1.setCategory("Programming");
        book1.setDescription("A book about writing clean, maintainable code");
        book1.setShelfLocation("A1-01");
        bookService.saveBook(book1);
        
        // Book 2
        Book book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setAuthor("Joshua Bloch");
        book2.setIsbn("9780134685991");
        book2.setPublicationDate(LocalDate.of(2017, 12, 27));
        book2.setPublisher("Addison-Wesley Professional");
        book2.setQuantity(3);
        book2.setCategory("Programming");
        book2.setDescription("Best practices for the Java platform");
        book2.setShelfLocation("A1-02");
        bookService.saveBook(book2);
        
        // Book 3
        Book book3 = new Book();
        book3.setTitle("Design Patterns: Elements of Reusable Object-Oriented Software");
        book3.setAuthor("Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides");
        book3.setIsbn("9780201633610");
        book3.setPublicationDate(LocalDate.of(1994, 11, 10));
        book3.setPublisher("Addison-Wesley Professional");
        book3.setQuantity(2);
        book3.setCategory("Programming");
        book3.setDescription("A book on software design patterns");
        book3.setShelfLocation("A1-03");
        bookService.saveBook(book3);
        
        // Book 4
        Book book4 = new Book();
        book4.setTitle("The Great Gatsby");
        book4.setAuthor("F. Scott Fitzgerald");
        book4.setIsbn("9780743273565");
        book4.setPublicationDate(LocalDate.of(1925, 4, 10));
        book4.setPublisher("Scribner");
        book4.setQuantity(10);
        book4.setCategory("Fiction");
        book4.setDescription("A novel about the American Dream");
        book4.setShelfLocation("B2-01");
        bookService.saveBook(book4);
        
        // Book 5
        Book book5 = new Book();
        book5.setTitle("To Kill a Mockingbird");
        book5.setAuthor("Harper Lee");
        book5.setIsbn("9780061120084");
        book5.setPublicationDate(LocalDate.of(1960, 7, 11));
        book5.setPublisher("HarperCollins");
        book5.setQuantity(7);
        book5.setCategory("Fiction");
        book5.setDescription("A novel about racial injustice in the American South");
        book5.setShelfLocation("B2-02");
        bookService.saveBook(book5);
    }
    
    private void createSampleUsers() {
        // Student 1
        User student1 = new User();
        student1.setFirstName("John");
        student1.setLastName("Doe");
        student1.setEmail("john.doe@example.com");
        student1.setPhoneNumber("1234567890");
        student1.setUserType(User.UserType.STUDENT);
        student1.setStudentId("S12345");
        student1.setTotalFinePending(0.0);
        student1.setCurrentIssuedBooks(0);
        userService.saveUser(student1);
        
        // Student 2
        User student2 = new User();
        student2.setFirstName("Jane");
        student2.setLastName("Smith");
        student2.setEmail("jane.smith@example.com");
        student2.setPhoneNumber("9876543210");
        student2.setUserType(User.UserType.STUDENT);
        student2.setStudentId("S67890");
        student2.setTotalFinePending(0.0);
        student2.setCurrentIssuedBooks(0);
        userService.saveUser(student2);
        
        // Faculty
        User faculty = new User();
        faculty.setFirstName("Robert");
        faculty.setLastName("Johnson");
        faculty.setEmail("robert.johnson@example.com");
        faculty.setPhoneNumber("5551234567");
        faculty.setUserType(User.UserType.FACULTY);
        faculty.setTotalFinePending(0.0);
        faculty.setCurrentIssuedBooks(0);
        userService.saveUser(faculty);
        
        // Librarian
        User librarian = new User();
        librarian.setFirstName("Emily");
        librarian.setLastName("Williams");
        librarian.setEmail("emily.williams@example.com");
        librarian.setPhoneNumber("7778889999");
        librarian.setUserType(User.UserType.LIBRARIAN);
        librarian.setTotalFinePending(0.0);
        librarian.setCurrentIssuedBooks(0);
        userService.saveUser(librarian);
        
        // Admin
        User admin = new User();
        admin.setFirstName("Michael");
        admin.setLastName("Brown");
        admin.setEmail("michael.brown@example.com");
        admin.setPhoneNumber("3334445555");
        admin.setUserType(User.UserType.ADMIN);
        admin.setTotalFinePending(0.0);
        admin.setCurrentIssuedBooks(0);
        userService.saveUser(admin);
    }
    
    private void createSampleBorrowings() {
        // Current borrowing
        borrowingService.borrowBook(1L, 1L, 14);
        
        // Overdue borrowing
        Borrowing overdueBorrowing = borrowingService.borrowBook(2L, 2L, 7);
        // Manually update the borrow date to make it overdue
        overdueBorrowing.setBorrowDate(LocalDate.now().minusDays(10));
        overdueBorrowing.setDueDate(LocalDate.now().minusDays(3));
        borrowingService.updateBorrowingStatus(overdueBorrowing.getId(), Borrowing.BorrowingStatus.OVERDUE);
        
        // Returned borrowing
        Borrowing returnedBorrowing = borrowingService.borrowBook(3L, 3L, 7);
        borrowingService.returnBook(returnedBorrowing.getId());
    }
    
    private void updateUserStatistics() {
        // Update user 1 (has 1 current borrowing)
        User user1 = userService.getUserById(1L).orElseThrow();
        user1.setCurrentIssuedBooks(1);
        userService.saveUser(user1);
        
        // Update user 2 (has 1 overdue borrowing with fine)
        User user2 = userService.getUserById(2L).orElseThrow();
        user2.setCurrentIssuedBooks(1);
        user2.setTotalFinePending(7.0); // 7 days overdue at $1 per day
        userService.saveUser(user2);
        
        // Update user 3 (has returned their book)
        User user3 = userService.getUserById(3L).orElseThrow();
        user3.setCurrentIssuedBooks(0);
        userService.saveUser(user3);
    }
} 