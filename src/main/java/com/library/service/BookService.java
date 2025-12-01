package com.library.service;

import com.library.model.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private List<Book> books;

    public BookService() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (findByIsbn(book.getIsbn()) != null) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        books.add(book);
    }

    public boolean removeBook(String isbn) {
        Book book = findByIsbn(isbn);
        if (book != null) {
            books.remove(book);
            return true;
        }
        return false;
    }

    public Book findByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    public int getTotalBooks() {
        return books.size();
    }
}
