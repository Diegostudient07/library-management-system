package com.library.service;

import com.library.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
    }

    @Test
    public void testAddBook() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        bookService.addBook(book);
        
        assertEquals(1, bookService.getTotalBooks());
    }

    @Test
    public void testAddDuplicateBook() {
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0-13-468599-1", "Another Title", "Another Author");
        
        bookService.addBook(book1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            bookService.addBook(book2);
        });
    }

    @Test
    public void testRemoveBook() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        bookService.addBook(book);
        
        assertTrue(bookService.removeBook("978-0-13-468599-1"));
        assertEquals(0, bookService.getTotalBooks());
    }

    @Test
    public void testRemoveNonExistentBook() {
        assertFalse(bookService.removeBook("978-9999999999"));
    }

    @Test
    public void testFindByIsbn() {
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        bookService.addBook(book);
        
        Book found = bookService.findByIsbn("978-0-13-468599-1");
        assertNotNull(found);
        assertEquals("Effective Java", found.getTitle());
        
        Book notFound = bookService.findByIsbn("978-9999999999");
        assertNull(notFound);
    }

    @Test
    public void testFindByTitle() {
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin");
        Book book3 = new Book("978-0-13-110362-7", "Java Programming", "Author");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        
        List<Book> results = bookService.findByTitle("java");
        assertEquals(2, results.size());
        
        results = bookService.findByTitle("clean");
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());
    }

    @Test
    public void testFindByAuthor() {
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin");
        Book book3 = new Book("978-0-13-110362-7", "Another Book", "Robert Smith");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        
        List<Book> results = bookService.findByAuthor("robert");
        assertEquals(2, results.size());
        
        results = bookService.findByAuthor("bloch");
        assertEquals(1, results.size());
        assertEquals("Joshua Bloch", results.get(0).getAuthor());
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        List<Book> allBooks = bookService.getAllBooks();
        assertEquals(2, allBooks.size());
    }

    @Test
    public void testGetAvailableBooks() {
        Book book1 = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        Book book2 = new Book("978-0-13-235088-4", "Clean Code", "Robert C. Martin");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        
        book1.setAvailable(false);
        
        List<Book> availableBooks = bookService.getAvailableBooks();
        assertEquals(1, availableBooks.size());
        assertEquals("Clean Code", availableBooks.get(0).getTitle());
    }
}
