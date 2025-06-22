package com.krowfeather.controller;

import com.krowfeather.entity.User;
import com.krowfeather.entity.Result;
import com.krowfeather.entity.Const;
import com.krowfeather.feign.UserFeignClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consumer/users")
public class UserController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/{id}")
//    @CircuitBreaker(name = "backendA", fallbackMethod = "getUserByIdFallback")
//    @Bulkhead(name = "bulkheadA",fallbackMethod = "getUserByIdBulkheadFallback")
    @RateLimiter(name = "ratelimiterA", fallbackMethod = "getUserByIdRateLimitFallback")
    public Result<User> getUserById(@PathVariable(name = "id") Integer id) {
//        try {
//            Thread.sleep(3000); // 模拟3秒的慢调用
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
        User user = userFeignClient.getUserById(id);
        System.out.println("success");
        return new Result<>(Const.CODE_SUCCESS, Const.MSG_SUCCESS, user);
    }

    public Result<User> getUserByIdRateLimitFallback(Integer id, Throwable t) {
        System.err.println("failed");
        return new Result<>(Const.CODE_ERROR, "限流降级：请求过多，请稍后再试", null);
    }

    public Result<User> getUserByIdBulkheadFallback(Integer id, Throwable t) {
        return new Result<>(Const.CODE_ERROR, "隔离器限流降级：服务繁忙", null);
    }

    public Result<User> getUserByIdFallback(Integer id, Throwable t) {
        return new Result<>(Const.CODE_ERROR, "断路器A降级响应：服务不可用", null);
    }

    @PostMapping
    @CircuitBreaker(name = "backendB", fallbackMethod = "createUserFallback")
    public Result<User> createUser(@RequestBody User user) {
        try {
            Thread.sleep(3000); // 模拟3秒的慢调用
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        User created = userFeignClient.createUser(user);
        return new Result<>(Const.CODE_SUCCESS, Const.MSG_SUCCESS, created);
    }

    public Result<User> createUserFallback(User user, Throwable t) {
        return new Result<>(Const.CODE_ERROR, "断路器B降级响应：服务不可用", null);
    }

    @PutMapping("/{id}")
    public Result<User> updateUser(@PathVariable(name = "id") Integer id, @RequestBody User user) {
        User updated = userFeignClient.updateUser(id, user);
        return new Result<>(Const.CODE_SUCCESS, Const.MSG_SUCCESS, updated);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUser(@PathVariable(name = "id") Integer id) {
        userFeignClient.deleteUser(id);
        return new Result<>(Const.CODE_SUCCESS, Const.MSG_SUCCESS, null);
    }
} 