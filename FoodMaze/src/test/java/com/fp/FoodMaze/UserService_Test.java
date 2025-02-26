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
import java.util.Optional;

public class UserServiceTests {

    @Mock
    private UserRepo userRepo;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepo);
    }

    @Test
    public void testUpdateUserInfo_Success() {
        int userId = 1;
        Long newPhone = 9876543210L;
        String newAddress = "New Address";
        String newName = "Updated Name";
        String newPassword = "newpassword123";

        // Using parameterized constructor to initialize User object
        User mockUser = new User(userId, newName, newPassword, newAddress, newPhone, "USER");
        
        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<String> response = userService.updateUserInfo(userId, newPhone, newAddress, newName, newPassword);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Information updated successfully!", response.getBody());
        assertEquals(newPhone, mockUser.getUserPhone());
        assertEquals(newAddress, mockUser.getUserAddress());
        assertEquals(newName, mockUser.getUserName());
        assertEquals(newPassword, mockUser.getUserPassword());
    }

    @Test
    public void testUpdateUserInfo_UserNotFound() {
        int userId = 1;
        Long newPhone = 9876543210L;
        
        when(userRepo.findById(userId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = userService.updateUserInfo(userId, newPhone, null, null, null);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not found!", response.getBody());
    }

    @Test
    public void testUpdateUserInfo_NoChanges() {
        int userId = 1;
        
        User mockUser = new User(userId, "Old Name", "oldpassword123", "Old Address", 1234567890L, "USER");
        
        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<String> response = userService.updateUserInfo(userId, null, null, null, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No information to update!", response.getBody());
    }

    @Test
    public void testUpdateUserInfo_InvalidInformation() {
        int userId = 1;
        
        User mockUser = new User(userId, "Old Name", "oldpassword123", "Old Address", 1234567890L, "USER");
        
        when(userRepo.findById(userId)).thenReturn(Optional.of(mockUser));

        // Act
        ResponseEntity<String> response = userService.updateUserInfo(userId, null, null, null, null);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No information to update!", response.getBody());
    }
}