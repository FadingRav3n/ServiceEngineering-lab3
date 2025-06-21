package com.krowfeather.controller;

import com.krowfeather.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @GetMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") Integer id){
        User user = new User();
        user.setId(id);
        user.setUsername("xiaoming");
        user.setPassword("123456");
        user.setAge(18);
        user.setGender("male");
        return user;
    }
}
