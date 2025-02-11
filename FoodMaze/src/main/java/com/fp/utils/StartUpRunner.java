package com.fp.utils;

import org.springframework.context.event.EventListener;
import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import com.fp.entity.User;
import com.fp.repository.UserRepo;

@Component
public class StartUpRunner {

    private UserRepo userRepo;

    public StartUpRunner(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @EventListener
    public void onApplicationReady(ApplicationReadyEvent event) {
        List<User> list = userRepo.findByUserType("Admin");
        if (list.size() == 0) {
            User user = new User();
            user.setUserName("Admin");
            user.setUserPassword("Admin@123");
            user.setUserPhone(1234567890);
            user.setUserType("Admin");
            userRepo.save(user);
        }
    }
}
