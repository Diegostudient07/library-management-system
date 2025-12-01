package com.library.service;

import com.library.model.Loan;
import com.library.model.User;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private List<User> users;

    public UserService() {
        this.users = new ArrayList<>();
    }

    public void registerUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (findUserById(user.getUserId()) != null) {
            throw new IllegalArgumentException("User with ID " + user.getUserId() + " already exists");
        }
        users.add(user);
    }

    public boolean removeUser(String userId) {
        User user = findUserById(userId);
        if (user == null) {
            return false;
        }
        if (!user.getActiveLoans().isEmpty()) {
            throw new IllegalStateException("Cannot remove user with active loans");
        }
        users.remove(user);
        return true;
    }

    public User findUserById(String userId) {
        return users.stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public List<Loan> getUserLoans(String userId) {
        User user = findUserById(userId);
        if (user == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(user.getActiveLoans());
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public int getTotalUsers() {
        return users.size();
    }
}
