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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @MockBean
    private UserRepo userRepo;  

    @Autowired
    private UserService userService;  

    private User mockUser;

    @BeforeEach
    public void setUp() {
       
        mockUser = new User();
        mockUser.setUserId(1);  
        mockUser.setUserPhone(1234567890L);
        mockUser.setUserPassword("password123");
        mockUser.setUserType("admin");

        
        when(userRepo.findByUserId(1)).thenReturn(mockUser);
    }

    @Test
    public void shouldReturnUserWhenUserIdIsValid() {
        // Act
        ResponseEntity<User> response = userService.fetchUserById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());  
        assertEquals(mockUser, response.getBody());  
    }

    @Test
    public void shouldReturnNotFoundWhenUserIdDoesNotExist() {
       
        when(userRepo.findByUserId(999)).thenReturn(null);

        // Act
        ResponseEntity<User> response = userService.fetchUserById(999);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());  
    }
}