package com.library.service;

import com.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookService = new BookService();
        book1 = new Book("978-0451524935", "1984", "George Orwell");
        book2 = new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee");
    }

    @Test
    void testAddBook() {
        bookService.addBook(book1);
        
        assertEquals(1, bookService.getTotalBooks());
        assertEquals(book1, bookService.findByIsbn("978-0451524935"));
    }

    @Test
    void testAddDuplicateBook() {
        bookService.addBook(book1);
        
        Book duplicate = new Book("978-0451524935", "Different Title", "Different Author");
        assertThrows(IllegalArgumentException.class, () -> 
            bookService.addBook(duplicate));
    }

    @Test
    void testRemoveBook() {
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        assertTrue(bookService.removeBook("978-0451524935"));
        assertEquals(1, bookService.getTotalBooks());
        assertNull(bookService.findByIsbn("978-0451524935"));
    }

    @Test
    void testRemoveNonExistentBook() {
        assertFalse(bookService.removeBook("non-existent-isbn"));
    }

    @Test
    void testFindByIsbn() {
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        Book found = bookService.findByIsbn("978-0061120084");
        assertEquals(book2, found);
        
        assertNull(bookService.findByIsbn("non-existent"));
    }

    @Test
    void testFindByTitle() {
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        List<Book> found = bookService.findByTitle("1984");
        assertEquals(1, found.size());
        assertEquals(book1, found.get(0));
        
        // Case insensitive search
        List<Book> foundCaseInsensitive = bookService.findByTitle("mockingbird");
        assertEquals(1, foundCaseInsensitive.size());
        assertEquals(book2, foundCaseInsensitive.get(0));
    }

    @Test
    void testFindByAuthor() {
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        List<Book> found = bookService.findByAuthor("Orwell");
        assertEquals(1, found.size());
        assertEquals(book1, found.get(0));
        
        // Case insensitive search
        List<Book> foundCaseInsensitive = bookService.findByAuthor("harper");
        assertEquals(1, foundCaseInsensitive.size());
        assertEquals(book2, foundCaseInsensitive.get(0));
    }

    @Test
    void testGetAllBooks() {
        assertTrue(bookService.getAllBooks().isEmpty());
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(2, allBooks.size());
        assertTrue(allBooks.contains(book1));
        assertTrue(allBooks.contains(book2));
    }

    @Test
    void testGetAvailableBooks() {
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        assertEquals(2, bookService.getAvailableBooks().size());
        
        book1.setAvailable(false);
        List<Book> availableBooks = bookService.getAvailableBooks();
        assertEquals(1, availableBooks.size());
        assertEquals(book2, availableBooks.get(0));
    }

    @Test
    void testGetTotalBooks() {
        assertEquals(0, bookService.getTotalBooks());
        
        bookService.addBook(book1);
        assertEquals(1, bookService.getTotalBooks());
        
        bookService.addBook(book2);
        assertEquals(2, bookService.getTotalBooks());
    }

    @Test
    void testAddNullBook() {
        assertThrows(IllegalArgumentException.class, () -> 
            bookService.addBook(null));
    }
}
