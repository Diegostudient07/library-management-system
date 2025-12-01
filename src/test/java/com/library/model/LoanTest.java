package com.library.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void testCreateLoan() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(LocalDate.now(), loan.getLoanDate());
        assertNull(loan.getReturnDate());
        assertFalse(book.isAvailable());
        assertTrue(user.getActiveLoans().contains(loan));
    }

    @Test
    void testCalculateReturnDate() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        LocalDate expectedDueDate = LocalDate.now().plusDays(Loan.LOAN_PERIOD_DAYS);
        
        assertEquals(expectedDueDate, loan.getDueDate());
    }

    @Test
    void testReturnBook() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        assertFalse(book.isAvailable());
        
        loan.returnBook();
        
        assertTrue(book.isAvailable());
        assertNotNull(loan.getReturnDate());
        assertFalse(user.getActiveLoans().contains(loan));
    }

    @Test
    void testIsOverdue() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        
        // Loan just created should not be overdue
        assertFalse(loan.isOverdue());
        assertEquals(0, loan.getDaysOverdue());
    }

    @Test
    void testCalculateLateFee() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        
        // Loan just created should have no late fee
        assertEquals(0.0, loan.calculateLateFee());
    }

    @Test
    void testLoanBookNotAvailable() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user1 = new User("U001", "Alice", "alice@email.com");
        User user2 = new User("U002", "Bob", "bob@email.com");
        
        // First loan should succeed
        new Loan(book, user1);
        
        // Second loan should fail because book is not available
        assertThrows(IllegalStateException.class, () -> 
            new Loan(book, user2));
    }

    @Test
    void testNullBook() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Loan(null, user));
    }

    @Test
    void testNullUser() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        
        assertThrows(IllegalArgumentException.class, () -> 
            new Loan(book, null));
    }

    @Test
    void testToString() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        User user = new User("U001", "Alice", "alice@email.com");
        
        Loan loan = new Loan(book, user);
        String toString = loan.toString();
        
        assertTrue(toString.contains("1984"));
        assertTrue(toString.contains("Alice"));
    }
}
