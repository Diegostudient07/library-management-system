package com.library.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
    public static final int LOAN_PERIOD_DAYS = 14;
    public static final double LATE_FEE_PER_DAY = 1.0;
    
    private Book book;
    private User user;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Loan(Book book, User user) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (!book.isAvailable()) {
            throw new IllegalStateException("Book is not available for loan");
        }
        this.book = book;
        this.user = user;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(LOAN_PERIOD_DAYS);
        this.returnDate = null;
        
        // Mark book as unavailable and add loan to user
        book.setAvailable(false);
        user.addLoan(this);
    }

    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.book.setAvailable(true);
        this.user.removeLoan(this);
    }

    public boolean isOverdue() {
        LocalDate checkDate = returnDate != null ? returnDate : LocalDate.now();
        return checkDate.isAfter(dueDate);
    }

    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        LocalDate checkDate = returnDate != null ? returnDate : LocalDate.now();
        return ChronoUnit.DAYS.between(dueDate, checkDate);
    }

    public double calculateLateFee() {
        return getDaysOverdue() * LATE_FEE_PER_DAY;
    }

    // Getters
    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "book=" + book.getTitle() +
                ", user=" + user.getName() +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
