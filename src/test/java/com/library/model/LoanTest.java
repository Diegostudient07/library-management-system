package com.library.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class LoanTest {

    @Test
    public void testCreateLoan() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        
        Loan loan = new Loan(book, user);
        
        assertEquals(book, loan.getBook());
        assertEquals(user, loan.getUser());
        assertEquals(LocalDate.now(), loan.getLoanDate());
        assertNotNull(loan.getDueDate());
        assertNull(loan.getReturnDate());
        assertFalse(book.isAvailable());
        assertEquals(1, user.getActiveLoans().size());
    }

    @Test
    public void testCalculateReturnDate() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        
        Loan loan = new Loan(book, user);
        
        LocalDate expectedDueDate = LocalDate.now().plusDays(14);
        assertEquals(expectedDueDate, loan.getDueDate());
    }

    @Test
    public void testReturnBook() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        
        Loan loan = new Loan(book, user);
        assertFalse(book.isAvailable());
        assertEquals(1, user.getActiveLoans().size());
        
        loan.returnBook();
        
        assertEquals(LocalDate.now(), loan.getReturnDate());
        assertTrue(book.isAvailable());
        assertEquals(0, user.getActiveLoans().size());
    }

    @Test
    public void testIsOverdue() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        
        Loan loan = new Loan(book, user);
        
        assertFalse(loan.isOverdue());
    }

    @Test
    public void testCalculateLateFee() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        
        Loan loan = new Loan(book, user);
        
        assertEquals(0.0, loan.calculateLateFee(), 0.01);
    }

    @Test
    public void testLoanBookNotAvailable() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user1 = new User("U001", "Diego", "diego@example.com");
        User user2 = new User("U002", "María", "maria@example.com");
        
        new Loan(book, user1);
        
        assertThrows(IllegalStateException.class, () -> {
            new Loan(book, user2);
        });
    }

    @Test
    public void testNullBook() {
        User user = new User("U001", "Diego", "diego@example.com");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Loan(null, user);
        });
    }

    @Test
    public void testNullUser() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Loan(book, null);
        });
    }

    @Test
    public void testToString() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        Loan loan = new Loan(book, user);
        
        String result = loan.toString();
        assertTrue(result.contains("Effective Java"));
        assertTrue(result.contains("Diego"));
        assertTrue(result.contains("returnDate=null"));
    }

    @Test
    public void testToStringAfterReturn() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        Loan loan = new Loan(book, user);
        loan.returnBook();
        
        String result = loan.toString();
        assertFalse(result.contains("returnDate=null"));
    }

    @Test
    public void testGetters() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        Loan loan = new Loan(book, user);
        
        assertNotNull(loan.getBook());
        assertEquals(book, loan.getBook());
        
        assertNotNull(loan.getUser());
        assertEquals(user, loan.getUser());
        
        assertNotNull(loan.getLoanDate());
        assertNotNull(loan.getDueDate());
        assertNull(loan.getReturnDate());
    }

    @Test
    public void testGetReturnDateAfterReturn() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        User user = new User("U001", "Diego", "diego@example.com");
        Loan loan = new Loan(book, user);
        
        assertNull(loan.getReturnDate());
        loan.returnBook();
        assertNotNull(loan.getReturnDate());
    }
}
