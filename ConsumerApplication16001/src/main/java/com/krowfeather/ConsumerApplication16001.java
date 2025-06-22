package com.krowfeather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import com.krowfeather.rule.CustomLoadBalanceConfig;

@SpringBootApplication
@EnableFeignClients
@LoadBalancerClient(name="provider-service",configuration = CustomLoadBalanceConfig.class)
public class ConsumerApplication16001 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication16001.class,args);
    }

}