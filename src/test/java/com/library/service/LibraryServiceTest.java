package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class LibraryServiceTest {
    private BookService bookService;
    private UserService userService;
    private LibraryService libraryService;
    private Book book1;
    private Book book2;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
        userService = new UserService();
        libraryService = new LibraryService(bookService, userService);
        
        book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        user1 = new User("U001", "Diego", "diego@example.com");
        user2 = new User("U002", "María", "maria@example.com");
        
        userService.registerUser(user1);
        userService.registerUser(user2);
    }

    @Test
    public void testLoanBook_Success() {
        Loan loan = libraryService.loanBook("978-0-13-468599-1", "U001");
        
        assertNotNull(loan);
        assertEquals(book1, loan.getBook());
        assertEquals(user1, loan.getUser());
        assertFalse(book1.isAvailable());
        assertEquals(1, libraryService.getActiveLoans().size());
    }

    @Test
    public void testLoanBook_BookNotAvailable() {
        libraryService.loanBook("978-0-13-468599-1", "U001");
        
        assertThrows(IllegalStateException.class, () -> {
            libraryService.loanBook("978-0-13-468599-1", "U002");
        });
    }

    @Test
    public void testLoanBook_BookNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.loanBook("978-9999999999", "U001");
        });
    }

    @Test
    public void testLoanBook_UserNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.loanBook("978-0-13-468599-1", "U999");
        });
    }

    @Test
    public void testLoanBook_UserExceededLimit() {
        Book book3 = new Book("978-0-13-110362-7", "The Pragmatic Programmer", "Andrew Hunt");
        Book book4 = new Book("978-0-596-52068-7", "JavaScript: The Good Parts", "Douglas Crockford");
        
        bookService.addBook(book3);
        bookService.addBook(book4);
        
        libraryService.loanBook("978-0-13-468599-1", "U001");
        libraryService.loanBook("978-0-13-235088-4", "U001");
        libraryService.loanBook("978-0-13-110362-7", "U001");
        
        assertThrows(IllegalStateException.class, () -> {
            libraryService.loanBook("978-0-596-52068-7", "U001");
        });
    }

    @Test
    public void testReturnBook() {
        libraryService.loanBook("978-0-13-468599-1", "U001");
        assertFalse(book1.isAvailable());
        
        libraryService.returnBook("978-0-13-468599-1", "U001");
        
        assertTrue(book1.isAvailable());
        assertEquals(0, libraryService.getActiveLoans().size());
    }

    @Test
    public void testIsBookAvailable() {
        assertTrue(libraryService.isBookAvailable("978-0-13-468599-1"));
        
        libraryService.loanBook("978-0-13-468599-1", "U001");
        
        assertFalse(libraryService.isBookAvailable("978-0-13-468599-1"));
        assertFalse(libraryService.isBookAvailable("978-9999999999"));
    }

    @Test
    public void testCanUserBorrow() {
        assertTrue(libraryService.canUserBorrow("U001"));
        
        Book book3 = new Book("978-0-13-110362-7", "The Pragmatic Programmer", "Andrew Hunt");
        bookService.addBook(book3);
        
        libraryService.loanBook("978-0-13-468599-1", "U001");
        libraryService.loanBook("978-0-13-235088-4", "U001");
        libraryService.loanBook("978-0-13-110362-7", "U001");
        
        assertFalse(libraryService.canUserBorrow("U001"));
        assertFalse(libraryService.canUserBorrow("U999"));
    }

    @Test
    public void testGetActiveLoans() {
        assertEquals(0, libraryService.getActiveLoans().size());
        
        libraryService.loanBook("978-0-13-468599-1", "U001");
        libraryService.loanBook("978-0-13-235088-4", "U002");
        
        assertEquals(2, libraryService.getActiveLoans().size());
        
        libraryService.returnBook("978-0-13-468599-1", "U001");
        
        assertEquals(1, libraryService.getActiveLoans().size());
    }

    @Test
    public void testCalculateLateFee() {
        Loan loan = libraryService.loanBook("978-0-13-468599-1", "U001");
        
        double fee = libraryService.calculateLateFee(loan);
        assertEquals(0.0, fee, 0.01);
        
        double feeNull = libraryService.calculateLateFee(null);
        assertEquals(0.0, feeNull, 0.01);
    }

    @Test
    public void testMultipleLoansAndReturns() {
        libraryService.loanBook("978-0-13-468599-1", "U001");
        libraryService.loanBook("978-0-13-235088-4", "U002");
        assertEquals(2, libraryService.getActiveLoans().size());
        
        libraryService.returnBook("978-0-13-468599-1", "U001");
        assertEquals(1, libraryService.getActiveLoans().size());
        
        libraryService.returnBook("978-0-13-235088-4", "U002");
        assertEquals(0, libraryService.getActiveLoans().size());
    }

    @Test
    public void testLoanBookNullIsbn() {
        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.loanBook(null, "U001");
        });
    }

    @Test
    public void testLoanBookNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            libraryService.loanBook("978-0-13-468599-1", null);
        });
    }
}
