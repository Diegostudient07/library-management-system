package com.library;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LibraryService;
import com.library.service.UserService;

public class Main {
    public static void main(String[] args) {
        // Create services
        BookService bookService = new BookService();
        UserService userService = new UserService();
        LibraryService libraryService = new LibraryService(bookService, userService);

        // Add sample books
        Book book1 = new Book("978-0451524935", "1984", "George Orwell");
        Book book2 = new Book("978-0061120084", "To Kill a Mockingbird", "Harper Lee");
        Book book3 = new Book("978-0743273565", "The Great Gatsby", "F. Scott Fitzgerald");
        
        bookService.addBook(book1);
        bookService.addBook(book2);
        bookService.addBook(book3);
        
        System.out.println("=== Library Management System Demo ===\n");
        System.out.println("Books added to library:");
        bookService.getAllBooks().forEach(System.out::println);

        // Register sample users
        User alice = new User("U001", "Alice Johnson", "alice@email.com");
        User bob = new User("U002", "Bob Smith", "bob@email.com");
        
        userService.registerUser(alice);
        userService.registerUser(bob);
        
        System.out.println("\nRegistered users:");
        userService.getAllUsers().forEach(System.out::println);

        // Perform loan operations
        System.out.println("\n=== Loan Operations ===\n");
        
        Loan loan1 = libraryService.loanBook("978-0451524935", "U001");
        System.out.println("Loan created: " + loan1);
        
        Loan loan2 = libraryService.loanBook("978-0061120084", "U002");
        System.out.println("Loan created: " + loan2);

        // Display results
        System.out.println("\nAvailable books after loans:");
        bookService.getAvailableBooks().forEach(System.out::println);
        
        System.out.println("\nActive loans:");
        libraryService.getActiveLoans().forEach(System.out::println);
        
        // Return a book
        System.out.println("\n=== Returning a Book ===\n");
        libraryService.returnBook("978-0451524935", "U001");
        System.out.println("Book '1984' returned by Alice");
        
        System.out.println("\nAvailable books after return:");
        bookService.getAvailableBooks().forEach(System.out::println);
        
        System.out.println("\nActive loans after return:");
        libraryService.getActiveLoans().forEach(System.out::println);
        
        System.out.println("\n=== System Statistics ===");
        System.out.println("Total books: " + bookService.getTotalBooks());
        System.out.println("Total users: " + userService.getTotalUsers());
        System.out.println("Total loans (all time): " + libraryService.getAllLoans().size());
        System.out.println("Active loans: " + libraryService.getActiveLoans().size());
        
        System.out.println("\n=== Demo Complete ===");
    }
}