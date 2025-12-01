package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryService {
    private BookService bookService;
    private UserService userService;
    private List<Loan> allLoans;

    public LibraryService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.allLoans = new ArrayList<>();
    }

    public Loan loanBook(String isbn, String userId) {
        Book book = bookService.findByIsbn(isbn);
        if (book == null) {
            throw new IllegalArgumentException("Book with ISBN " + isbn + " not found");
        }
        
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " not found");
        }
        
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for loan");
        }
        
        if (!user.canBorrow()) {
            throw new IllegalStateException("User has exceeded maximum loan limit");
        }
        
        Loan loan = new Loan(book, user);
        allLoans.add(loan);
        return loan;
    }

    public void returnBook(String isbn, String userId) {
        Loan loan = findActiveLoan(isbn, userId);
        if (loan == null) {
            throw new IllegalArgumentException("No active loan found for this book and user");
        }
        loan.returnBook();
    }

    public boolean isBookAvailable(String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return book != null && book.isAvailable();
    }

    public boolean canUserBorrow(String userId) {
        User user = userService.findUserById(userId);
        return user != null && user.canBorrow();
    }

    public double calculateLateFee(Loan loan) {
        return loan.calculateLateFee();
    }

    public List<Loan> getOverdueLoans() {
        return allLoans.stream()
                .filter(loan -> loan.getReturnDate() == null && loan.isOverdue())
                .collect(Collectors.toList());
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<>(allLoans);
    }

    public List<Loan> getActiveLoans() {
        return allLoans.stream()
                .filter(loan -> loan.getReturnDate() == null)
                .collect(Collectors.toList());
    }

    private Loan findActiveLoan(String isbn, String userId) {
        return allLoans.stream()
                .filter(loan -> loan.getBook().getIsbn().equals(isbn))
                .filter(loan -> loan.getUser().getUserId().equals(userId))
                .filter(loan -> loan.getReturnDate() == null)
                .findFirst()
                .orElse(null);
    }
}
