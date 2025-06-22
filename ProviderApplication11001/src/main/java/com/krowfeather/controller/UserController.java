package com.krowfeather.controller;

import com.krowfeather.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final Map<Integer, User> userMap = new ConcurrentHashMap<>();
    {
        userMap.put(1, new User(1, "user1", "password1", "male", 18));
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(name = "id") Integer id){
        User user = userMap.get(id);
        User result = new User(
                user.getId(),
                user.getUsername() + "11001",
                user.getPassword(),
                user.getGender(),
                user.getAge()
        );
        return result;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        userMap.put(user.getId(), user);
        return user;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(name = "id") Integer id, @RequestBody User user) {
        user.setId(id);
        userMap.put(id, user);
        return user;
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id) {
        userMap.remove(id);
        return "Deleted";
    }
}
