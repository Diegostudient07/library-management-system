package com.library.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    @Test
    public void testCreateBook() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        assertEquals("978-0-13-468599-1", book.getIsbn());
        assertEquals("Effective Java", book.getTitle());
        assertEquals("Joshua Bloch", book.getAuthor());
        assertTrue(book.isAvailable());
    }

    @Test
    public void testBookAvailability() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        assertTrue(book.isAvailable());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    public void testInvalidISBN() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(null, "Effective Java", "Joshua Bloch");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("", "Effective Java", "Joshua Bloch");
        });
    }

    @Test
    public void testInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("978-0-13-468599-1", null, "Joshua Bloch");
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("978-0-13-468599-1", "", "Joshua Bloch");
        });
    }

    @Test
    public void testInvalidAuthor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("978-0-13-468599-1", "Effective Java", null);
        });
        
        assertThrows(IllegalArgumentException.class, () -> {
            new Book("978-0-13-468599-1", "Effective Java", "");
        });
    }

    @Test
    public void testSetters() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        book.setIsbn("978-0-13-235088-4");
        assertEquals("978-0-13-235088-4", book.getIsbn());
        
        book.setTitle("Clean Code");
        assertEquals("Clean Code", book.getTitle());
        
        book.setAuthor("Robert C. Martin");
        assertEquals("Robert C. Martin", book.getAuthor());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    public void testToString() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        String result = book.toString();
        
        assertTrue(result.contains("978-0-13-468599-1"));
        assertTrue(result.contains("Effective Java"));
        assertTrue(result.contains("Joshua Bloch"));
        assertTrue(result.contains("available=true"));
    }

    @Test
    public void testToStringNotAvailable() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        book.setAvailable(false);
        String result = book.toString();
        
        assertTrue(result.contains("available=false"));
    }
}
