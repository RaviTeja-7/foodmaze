package com.fp.FoodMaze;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fp.entity.User;
import com.fp.repository.UserRepo;
import com.fp.service.UserService;

import java.util.Arrays;
import java.util.List;

@SpringBootTest  
@ExtendWith(MockitoExtension.class)  
public class UserServiceTests {

    @MockBean
    private UserRepo userRepo; 

    @Autowired
    private UserService userService; 

    private User mockUser1;
    private User mockUser2;

    @BeforeEach
    public void setUp() {
        
        mockUser1 = new User();
        mockUser1.setUserPhone(1234567890L);
        mockUser1.setUserPassword("password123");
        mockUser1.setUserType("admin");

        mockUser2 = new User();
        mockUser2.setUserPhone(9876543210L);
        mockUser2.setUserPassword("password123");
        mockUser2.setUserType("admin");

        
        when(userRepo.findByUserType("admin")).thenReturn(Arrays.asList(mockUser1, mockUser2));
    }

    @Test
    public void shouldReturnUsersWhenUserTypeIsValid() {
        // Act
        ResponseEntity<List<User>> response = userService.fetchByUserType("admin");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(2, response.getBody().size());  
        assertEquals("admin", response.getBody().get(0).getUserType());  
    }

    @Test
    public void shouldReturnEmptyListWhenNoUsersFound() {
        
        when(userRepo.findByUserType("guest")).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<User>> response = userService.fetchByUserType("guest");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode()); 
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void shouldReturnInternalServerErrorWhenExceptionOccurs() {
        
        when(userRepo.findByUserType("admin")).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<List<User>> response = userService.fetchByUserType("admin");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
        assertTrue(response.getBody().isEmpty()); 
    }
}