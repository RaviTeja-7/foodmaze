package com.fp.repository;

import org.springframework.data.repository.CrudRepository;
import com.fp.entity.User;
import java.util.List;

public interface UserRepo extends CrudRepository<User, Integer> {

    List<User> findAll();

    User findByUserId(int userId);

    List<User> findByUserType(String userType);

    User findByUserPhone(Long userPhone);
}