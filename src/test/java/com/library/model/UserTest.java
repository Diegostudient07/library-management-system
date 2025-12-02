package com.library.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testCreateUser() {
        User user = new User("U001", "Diego", "diego@example.com");
        
        assertEquals("U001", user.getUserId());
        assertEquals("Diego", user.getName());
        assertEquals("diego@example.com", user.getEmail());
        assertTrue(user.getActiveLoans().isEmpty());
    }

    @Test
    public void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", "Diego", "invalidemail");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", "Diego", null);
        });
    }

    @Test
    public void testInvalidUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "Diego", "diego@example.com");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "Diego", "diego@example.com");
        });
    }

    @Test
    public void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", null, "diego@example.com");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new User("U001", "", "diego@example.com");
        });
    }

    @Test
    public void testCanBorrow() {
        User user = new User("U001", "Diego", "diego@example.com");
        Book book1 = new Book("978-1", "Book 1", "Author 1");
        Book book2 = new Book("978-2", "Book 2", "Author 2");
        Book book3 = new Book("978-3", "Book 3", "Author 3");
        
        assertTrue(user.canBorrow());
        
        Loan loan1 = new Loan(book1, user);
        assertTrue(user.canBorrow());
        
        Loan loan2 = new Loan(book2, user);
        assertTrue(user.canBorrow());
        
        Loan loan3 = new Loan(book3, user);
        assertFalse(user.canBorrow());
    }

    @Test
    public void testMaxLoans() {
        User user = new User("U001", "Diego", "diego@example.com");
        Book book1 = new Book("978-1", "Book 1", "Author 1");
        Book book2 = new Book("978-2", "Book 2", "Author 2");
        Book book3 = new Book("978-3", "Book 3", "Author 3");
        Book book4 = new Book("978-4", "Book 4", "Author 4");
        
        new Loan(book1, user);
        new Loan(book2, user);
        new Loan(book3, user);
        
        assertEquals(3, user.getActiveLoans().size());
        
        assertThrows(IllegalStateException.class, () -> {
            new Loan(book4, user);
        });
    }
}
