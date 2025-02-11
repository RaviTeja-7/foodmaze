package com.fp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.fp.entity.User;
import com.fp.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> fetchAllUsers() {
        return userService.fetchAllUsers();
    }

    @GetMapping(path = "/validateUser")
    public ResponseEntity<User> validateUser(@RequestHeader(value = "userPhone") Long userPhone,
            @RequestHeader(value = "userPassword") String userPassword) {
        return userService.validateUser(userPhone, userPassword);
    }

    @GetMapping(path = "/type")
    public ResponseEntity<User> fetchByType(@RequestHeader(name = "userType") String userType) {
        return userService.fetchUserById(Integer.parseInt(userType));
    }

    @GetMapping(path = "/id")
    public ResponseEntity<User> fetchById(@RequestHeader(value = "Id") int id) {
        return userService.fetchUserById(id);
    }

    @PutMapping(path = "/info")
    public ResponseEntity<String> updateUserInfo(@RequestHeader(value = "Id", required = true) int Id,
            @RequestHeader(value = "userPhone", required = false) Long phno,
            @RequestHeader(value = "userAddress", required = false) String userAddress,
            @RequestHeader(value = "userName", required = false) String userName,
            @RequestHeader(value = "userPassword", required = false) String userPassword
            ) {
        return userService.updateUserInfo(Id, phno, userAddress, userName, userPassword);
    }
}