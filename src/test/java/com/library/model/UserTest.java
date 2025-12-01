package com.library.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testCreateUser() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        assertEquals("U001", user.getUserId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@email.com", user.getEmail());
        assertTrue(user.getActiveLoans().isEmpty());
    }

    @Test
    void testInvalidEmail() {
        assertThrows(IllegalArgumentException.class, () -> 
            new User("U001", "Alice", "invalidemail"));
        assertThrows(IllegalArgumentException.class, () -> 
            new User("U001", "Alice", null));
    }

    @Test
    void testInvalidUserId() {
        assertThrows(IllegalArgumentException.class, () -> 
            new User(null, "Alice", "alice@email.com"));
        assertThrows(IllegalArgumentException.class, () -> 
            new User("", "Alice", "alice@email.com"));
    }

    @Test
    void testInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> 
            new User("U001", null, "alice@email.com"));
        assertThrows(IllegalArgumentException.class, () -> 
            new User("U001", "", "alice@email.com"));
    }

    @Test
    void testCanBorrow() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        assertTrue(user.canBorrow());
        
        // Add loans up to MAX_LOANS - 1
        for (int i = 0; i < User.MAX_LOANS - 1; i++) {
            Book book = new Book("ISBN-" + i, "Title " + i, "Author " + i);
            new Loan(book, user);
        }
        
        assertTrue(user.canBorrow());
        
        // Add one more loan to reach MAX_LOANS
        Book book = new Book("ISBN-LAST", "Last Title", "Last Author");
        new Loan(book, user);
        
        assertFalse(user.canBorrow());
    }

    @Test
    void testMaxLoans() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        // Fill up to max loans
        for (int i = 0; i < User.MAX_LOANS; i++) {
            Book book = new Book("ISBN-" + i, "Title " + i, "Author " + i);
            new Loan(book, user);
        }
        
        // Try to add one more loan should throw exception
        Book extraBook = new Book("ISBN-EXTRA", "Extra Title", "Extra Author");
        assertThrows(IllegalStateException.class, () -> 
            new Loan(extraBook, user));
    }

    @Test
    void testSetters() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        user.setUserId("U002");
        assertEquals("U002", user.getUserId());
        
        user.setName("Bob");
        assertEquals("Bob", user.getName());
        
        user.setEmail("bob@email.com");
        assertEquals("bob@email.com", user.getEmail());
    }

    @Test
    void testSetterValidation() {
        User user = new User("U001", "Alice", "alice@email.com");
        
        assertThrows(IllegalArgumentException.class, () -> user.setUserId(null));
        assertThrows(IllegalArgumentException.class, () -> user.setUserId(""));
        assertThrows(IllegalArgumentException.class, () -> user.setName(null));
        assertThrows(IllegalArgumentException.class, () -> user.setName(""));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalidemail"));
    }

    @Test
    void testToString() {
        User user = new User("U001", "Alice", "alice@email.com");
        String toString = user.toString();
        
        assertTrue(toString.contains("U001"));
        assertTrue(toString.contains("Alice"));
        assertTrue(toString.contains("alice@email.com"));
    }
}
