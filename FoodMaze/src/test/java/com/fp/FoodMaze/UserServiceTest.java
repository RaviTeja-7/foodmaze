package com.fp.FoodMaze;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fp.entity.User;
import com.fp.repository.UserRepo;
import com.fp.service.UserService;

public class UserServiceTest {

    @Mock
    private UserRepo userRepo;  

    @InjectMocks
    private UserService userService; 
    
    private User mockUser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  
        mockUser = new User();  
        mockUser.setUserPhone(1234567890L);  
        mockUser.setUserPassword("password123"); 
    }

    @Test
    public void testValidateUser_Success() {
        // Arrange
        when(userRepo.findByUserPhone(1234567890L)).thenReturn(mockUser);  

        // Act
        ResponseEntity<User> response = userService.validateUser(1234567890L, "password123");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(mockUser, response.getBody());  
    }

    @Test
    public void testValidateUser_Failure_InvalidPassword() {
        // Arrange
        when(userRepo.findByUserPhone(1234567890L)).thenReturn(mockUser);  

        // Act
        ResponseEntity<User> response = userService.validateUser(1234567890L, "wrongpassword");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());  
    }

    @Test
    public void testValidateUser_Failure_UserNotFound() {
        // Arrange
        when(userRepo.findByUserPhone(1234567890L)).thenReturn(null);  

        // Act
        ResponseEntity<User> response = userService.validateUser(1234567890L, "password123");

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());  
    }
}
