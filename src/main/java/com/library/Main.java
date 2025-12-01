package com.library;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create services
        BookService bookService = new BookService();
        UserService userService = new UserService();
        LibraryService libraryService = new LibraryService();

        // Add books
        bookService.addBook("1984", "George Orwell");
        bookService.addBook("To Kill a Mockingbird", "Harper Lee");

        // Register users
        userService.registerUser("Alice");
        userService.registerUser("Bob");

        // Loan transactions
        libraryService.loanBook("1984", "Alice");
        libraryService.loanBook("To Kill a Mockingbird", "Bob");

        // Display loan information
        List<String> loans = libraryService.getLoans();
        for (String loan : loans) {
            System.out.println(loan);
        }
    }
}