package com.library.model;

import java.util.ArrayList;
import java.util.List;

public class User {
    public static final int MAX_LOANS = 3;
    
    private String userId;
    private String name;
    private String email;
    private List<Loan> activeLoans;

    public User(String userId, String name, String email) {
        if (userId == null || userId.isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @");
        }
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.activeLoans = new ArrayList<>();
    }

    public void addLoan(Loan loan) {
        if (activeLoans.size() >= MAX_LOANS) {
            throw new IllegalStateException("User has reached maximum number of loans");
        }
        activeLoans.add(loan);
    }

    public void removeLoan(Loan loan) {
        activeLoans.remove(loan);
    }

    public boolean canBorrow() {
        return activeLoans.size() < MAX_LOANS;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Loan> getActiveLoans() {
        return activeLoans;
    }

    public void setActiveLoans(List<Loan> activeLoans) {
        this.activeLoans = activeLoans;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", activeLoans=" + activeLoans.size() +
                '}';
    }
}
