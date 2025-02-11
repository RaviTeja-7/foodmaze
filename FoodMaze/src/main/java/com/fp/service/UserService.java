package com.fp.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fp.entity.User;
import com.fp.repository.UserRepo;

@Service
public class UserService {

    private UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public ResponseEntity<User> createUser(User user) {
        userRepo.save(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    public ResponseEntity<List<User>> fetchAllUsers() {
        return new ResponseEntity<List<User>>(userRepo.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<User> fetchUserById(int userId) {
        return new ResponseEntity<User>(userRepo.findByUserId(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<User>> fetchByUserType(String userType) {
        return new ResponseEntity<List<User>>(userRepo.findByUserType(userType), HttpStatus.OK);
    }

    public ResponseEntity<User> validateUser(Long userPhone, String userPassword) {
        User user = userRepo.findByUserPhone(userPhone);
        if (user != null && user.getUserPassword().equals(userPassword))
            return new ResponseEntity<User>(user, HttpStatus.OK);
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateUserInfo(int id, Long phno, String address, String name, String password) {
        if (phno != null || address != null) {
            User user = userRepo.findById(id).get();
            if (phno != null)
                user.setUserPhone(phno);
            if (address != null)
                user.setUserAddress(address);
            if (name != null)
                user.setUserName(name);
            if (password != null)
                user.setUserPassword(password);
            userRepo.save(user);
            return new ResponseEntity<String>("User Information updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("User Information presented is invalid!", HttpStatus.BAD_REQUEST);
    }
}