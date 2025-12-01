package com.library.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testCreateBook() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        
        assertEquals("978-0451524935", book.getIsbn());
        assertEquals("1984", book.getTitle());
        assertEquals("George Orwell", book.getAuthor());
        assertTrue(book.isAvailable());
    }

    @Test
    void testBookAvailability() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        
        assertTrue(book.isAvailable());
        book.setAvailable(false);
        assertFalse(book.isAvailable());
        book.setAvailable(true);
        assertTrue(book.isAvailable());
    }

    @Test
    void testInvalidISBN() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book(null, "1984", "George Orwell"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("", "1984", "George Orwell"));
    }

    @Test
    void testInvalidTitle() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("978-0451524935", null, "George Orwell"));
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("978-0451524935", "", "George Orwell"));
    }

    @Test
    void testInvalidAuthor() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("978-0451524935", "1984", null));
        assertThrows(IllegalArgumentException.class, () -> 
            new Book("978-0451524935", "1984", ""));
    }

    @Test
    void testSetters() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        
        book.setIsbn("978-0061120084");
        assertEquals("978-0061120084", book.getIsbn());
        
        book.setTitle("New Title");
        assertEquals("New Title", book.getTitle());
        
        book.setAuthor("New Author");
        assertEquals("New Author", book.getAuthor());
        
        book.setAvailable(false);
        assertFalse(book.isAvailable());
    }

    @Test
    void testToString() {
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        String toString = book.toString();
        
        assertTrue(toString.contains("978-0451524935"));
        assertTrue(toString.contains("1984"));
        assertTrue(toString.contains("George Orwell"));
    }
}
