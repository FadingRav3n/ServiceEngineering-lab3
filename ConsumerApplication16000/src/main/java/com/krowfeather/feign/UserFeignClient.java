package com.krowfeather.feign;

import com.krowfeather.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "provider-service", path = "/api/users")
public interface UserFeignClient {
    @GetMapping("/{id}")
    User getUserById(@PathVariable("id") Integer id);

    @PostMapping
    User createUser(@RequestBody User user);

    @PutMapping("/{id}")
    User updateUser(@PathVariable("id") Integer id, @RequestBody User user);

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable("id") Integer id);
} 