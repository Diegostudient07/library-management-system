package com.library.service;

import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @Test
    public void testRegisterUser() {
        User user = new User("U001", "Diego", "diego@example.com");
        userService.registerUser(user);
        
        assertEquals(1, userService.getTotalUsers());
    }

    @Test
    public void testRegisterDuplicateUser() {
        User user1 = new User("U001", "Diego", "diego@example.com");
        User user2 = new User("U001", "María", "maria@example.com");
        
        userService.registerUser(user1);
        
        assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(user2);
        });
    }

    @Test
    public void testRemoveUser() {
        User user = new User("U001", "Diego", "diego@example.com");
        userService.registerUser(user);
        
        assertTrue(userService.removeUser("U001"));
        assertEquals(0, userService.getTotalUsers());
    }

    @Test
    public void testRemoveUserWithActiveLoans() {
        User user = new User("U001", "Diego", "diego@example.com");
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        userService.registerUser(user);
        new Loan(book, user);
        
        assertThrows(IllegalStateException.class, () -> {
            userService.removeUser("U001");
        });
    }

    @Test
    public void testFindUserById() {
        User user = new User("U001", "Diego", "diego@example.com");
        userService.registerUser(user);
        
        User found = userService.findUserById("U001");
        assertNotNull(found);
        assertEquals("Diego", found.getName());
        
        User notFound = userService.findUserById("U999");
        assertNull(notFound);
    }

    @Test
    public void testGetUserLoans() {
        User user = new User("U001", "Diego", "diego@example.com");
        Book book = new Book("978-0-13-468599-1", "Effective Java", "Joshua Bloch");
        
        userService.registerUser(user);
        new Loan(book, user);
        
        List<Loan> loans = userService.getUserLoans("U001");
        assertEquals(1, loans.size());
        
        List<Loan> noLoans = userService.getUserLoans("U999");
        assertEquals(0, noLoans.size());
    }

    @Test
    public void testGetAllUsers() {
        User user1 = new User("U001", "Diego", "diego@example.com");
        User user2 = new User("U002", "María", "maria@example.com");
        
        userService.registerUser(user1);
        userService.registerUser(user2);
        
        List<User> allUsers = userService.getAllUsers();
        assertEquals(2, allUsers.size());
    }
}
