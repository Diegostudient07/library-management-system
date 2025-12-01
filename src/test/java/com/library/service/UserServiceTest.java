package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        user1 = new User("U001", "Alice", "alice@email.com");
        user2 = new User("U002", "Bob", "bob@email.com");
    }

    @Test
    void testRegisterUser() {
        userService.registerUser(user1);
        
        assertEquals(1, userService.getTotalUsers());
        assertEquals(user1, userService.findUserById("U001"));
    }

    @Test
    void testRegisterDuplicateUser() {
        userService.registerUser(user1);
        
        User duplicate = new User("U001", "Different Name", "different@email.com");
        assertThrows(IllegalArgumentException.class, () -> 
            userService.registerUser(duplicate));
    }

    @Test
    void testRemoveUser() {
        userService.registerUser(user1);
        userService.registerUser(user2);
        
        assertTrue(userService.removeUser("U001"));
        assertEquals(1, userService.getTotalUsers());
        assertNull(userService.findUserById("U001"));
    }

    @Test
    void testRemoveNonExistentUser() {
        assertFalse(userService.removeUser("non-existent-id"));
    }

    @Test
    void testRemoveUserWithActiveLoans() {
        userService.registerUser(user1);
        
        // Create a loan for user1
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        new Loan(book, user1);
        
        assertThrows(IllegalStateException.class, () -> 
            userService.removeUser("U001"));
    }

    @Test
    void testFindUserById() {
        userService.registerUser(user1);
        userService.registerUser(user2);
        
        User found = userService.findUserById("U002");
        assertEquals(user2, found);
        
        assertNull(userService.findUserById("non-existent"));
    }

    @Test
    void testGetUserLoans() {
        userService.registerUser(user1);
        
        // Initially no loans
        List<Loan> loans = userService.getUserLoans("U001");
        assertTrue(loans.isEmpty());
        
        // Add a loan
        Book book = new Book("978-0451524935", "1984", "George Orwell");
        Loan loan = new Loan(book, user1);
        
        loans = userService.getUserLoans("U001");
        assertEquals(1, loans.size());
        assertTrue(loans.contains(loan));
    }

    @Test
    void testGetUserLoansNonExistentUser() {
        List<Loan> loans = userService.getUserLoans("non-existent");
        assertTrue(loans.isEmpty());
    }

    @Test
    void testGetAllUsers() {
        assertTrue(userService.getAllUsers().isEmpty());
        
        userService.registerUser(user1);
        userService.registerUser(user2);
        
        List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
        assertTrue(allUsers.contains(user1));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    void testGetTotalUsers() {
        assertEquals(0, userService.getTotalUsers());
        
        userService.registerUser(user1);
        assertEquals(1, userService.getTotalUsers());
        
        userService.registerUser(user2);
        assertEquals(2, userService.getTotalUsers());
    }

    @Test
    void testRegisterNullUser() {
        assertThrows(IllegalArgumentException.class, () -> 
            userService.registerUser(null));
    }
}
