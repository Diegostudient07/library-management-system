package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {
    private BookService bookService;
    private UserService userService;
    private LibraryService libraryService;
    private Book book1;
    private Book book2;
    private Book book3;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        userService = new UserService();
        libraryService = new LibraryService(bookService, userService);
        
        book1 = new Book("978-0451524935", "1984", "George Orwell");
        book2 = new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee");
        book3 = new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        
        user1 = new User("U001", "Alice", "alice@email.com");
        user2 = new User("U002", "Bob", "bob@email.com");
        
        userService.registerUser(user1);
        userService.registerUser(user2);
    }

    @Test
    void testLoanBook_Success() {
        Loan loan = libraryService.loanBook("978-0451524935", "U001");
        
        assertNotNull(loan);
        assertEquals(book1, loan.getBook());
        assertEquals(user1, loan.getUser());
        assertFalse(book1.isAvailable());
        assertEquals(1, libraryService.getActiveLoans().size());
    }

    @Test
    void testLoanBook_BookNotAvailable() {
        libraryService.loanBook("978-0451524935", "U001");
        
        assertThrows(IllegalStateException.class, () -> 
            libraryService.loanBook("978-0451524935", "U002"));
    }

    @Test
    void testLoanBook_BookNotFound() {
        assertThrows(IllegalArgumentException.class, () -> 
            libraryService.loanBook("non-existent-isbn", "U001"));
    }

    @Test
    void testLoanBook_UserNotFound() {
        assertThrows(IllegalArgumentException.class, () -> 
            libraryService.loanBook("978-0451524935", "non-existent-user"));
    }

    @Test
    void testLoanBook_UserExceededLimit() {
        // User1 borrows MAX_LOANS books
        libraryService.loanBook("978-0451524935", "U001");
        libraryService.loanBook("978-0061120084", "U001");
        libraryService.loanBook("978-0743273565", "U001");
        
        // Add another book
        Book book4 = new Book("978-0000000000", "Another Book", "Another Author");
        bookService.addBook(book4);
        
        // User1 should not be able to borrow more
        assertThrows(IllegalStateException.class, () -> 
            libraryService.loanBook("978-0000000000", "U001"));
    }

    @Test
    void testReturnBook() {
        libraryService.loanBook("978-0451524935", "U001");
        assertFalse(book1.isAvailable());
        
        libraryService.returnBook("978-0451524935", "U001");
        
        assertTrue(book1.isAvailable());
        assertEquals(0, libraryService.getActiveLoans().size());
    }

    @Test
    void testReturnBook_NoActiveLoan() {
        assertThrows(IllegalArgumentException.class, () -> 
            libraryService.returnBook("978-0451524935", "U001"));
    }

    @Test
    void testIsBookAvailable() {
        assertTrue(libraryService.isBookAvailable("978-0451524935"));
        
        libraryService.loanBook("978-0451524935", "U001");
        
        assertFalse(libraryService.isBookAvailable("978-0451524935"));
    }

    @Test
    void testIsBookAvailable_BookNotFound() {
        assertFalse(libraryService.isBookAvailable("non-existent-isbn"));
    }

    @Test
    void testCanUserBorrow() {
        assertTrue(libraryService.canUserBorrow("U001"));
        
        // Borrow MAX_LOANS books
        libraryService.loanBook("978-0451524935", "U001");
        libraryService.loanBook("978-0061120084", "U001");
        libraryService.loanBook("978-0743273565", "U001");
        
        assertFalse(libraryService.canUserBorrow("U001"));
    }

    @Test
    void testCanUserBorrow_UserNotFound() {
        assertFalse(libraryService.canUserBorrow("non-existent-user"));
    }

    @Test
    void testGetActiveLoans() {
        assertTrue(libraryService.getActiveLoans().isEmpty());
        
        libraryService.loanBook("978-0451524935", "U001");
        libraryService.loanBook("978-0061120084", "U002");
        
        List<Loan> activeLoans = libraryService.getActiveLoans();
        assertEquals(2, activeLoans.size());
        
        // Return one book
        libraryService.returnBook("978-0451524935", "U001");
        
        activeLoans = libraryService.getActiveLoans();
        assertEquals(1, activeLoans.size());
    }

    @Test
    void testGetAllLoans() {
        libraryService.loanBook("978-0451524935", "U001");
        libraryService.loanBook("978-0061120084", "U002");
        
        libraryService.returnBook("978-0451524935", "U001");
        
        // All loans should include returned ones
        List<Loan> allLoans = libraryService.getAllLoans();
        assertEquals(2, allLoans.size());
    }

    @Test
    void testCalculateLateFee() {
        Loan loan = libraryService.loanBook("978-0451524935", "U001");
        
        // Loan just created should have no late fee
        double lateFee = libraryService.calculateLateFee(loan);
        assertEquals(0.0, lateFee);
    }

    @Test
    void testGetOverdueLoans() {
        libraryService.loanBook("978-0451524935", "U001");
        
        // Loans just created should not be overdue
        List<Loan> overdueLoans = libraryService.getOverdueLoans();
        assertTrue(overdueLoans.isEmpty());
    }
}
