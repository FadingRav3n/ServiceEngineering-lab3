package com.krowfeather.controller;

import com.krowfeather.entity.Blog;
import com.krowfeather.entity.Const;
import com.krowfeather.entity.Result;
import com.krowfeather.entity.User;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {
    @Resource
    private RestTemplate restTemplate;
    @Resource
    private DiscoveryClient discoveryClient;
    @GetMapping("/{id}")
    public Result<Blog> getBlogById(@PathVariable(name = "id") Integer id){
        List<ServiceInstance> instanceList = discoveryClient.getInstances("provider-service");
        ServiceInstance instance = instanceList.getFirst();
        Blog blog = new Blog();
        blog.setId(id);
        blog.setTitle("hello");
        blog.setContent("my first blog");
        blog.setLikes(10);
        int uid = 2;
        User user = restTemplate.getForObject("http://"+instance.getHost()+":"+instance.getPort()+"/api/users/" + uid, User.class);
        blog.setUser(user);
        return new Result<>(Const.CODE_SUCCESS,Const.MSG_SUCCESS,blog);
    }
}
