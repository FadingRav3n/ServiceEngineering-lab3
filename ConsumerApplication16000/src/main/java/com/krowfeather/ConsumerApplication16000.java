package com.krowfeather;

import com.krowfeather.rule.CustomLoadBalanceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(name="provider-service",configuration = CustomLoadBalanceConfig.class)
public class ConsumerApplication16000 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication16000.class,args);
    }

}