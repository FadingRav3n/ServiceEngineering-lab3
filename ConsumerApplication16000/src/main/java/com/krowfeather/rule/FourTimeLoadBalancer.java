package com.krowfeather.rule;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.DefaultResponse;
import org.springframework.cloud.client.loadbalancer.EmptyResponse;
import org.springframework.cloud.client.loadbalancer.Request;
import org.springframework.cloud.client.loadbalancer.Response;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;

public class FourTimeLoadBalancer implements ReactorServiceInstanceLoadBalancer {
    private int instance_call_count = 0; //已经被调用的次数
    private int instance_index = 0;//当前提供服务的实例
    private static final int CALLS_PER_INSTANCE = 4; // 每个实例连续调用4次
    private final String serviceId;
    private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers;


    public FourTimeLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSuppliers, String serviceId) {
        this.serviceInstanceListSuppliers = serviceInstanceListSuppliers;
        this.serviceId = serviceId;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier=this.serviceInstanceListSuppliers.getIfAvailable();
        return supplier.get().next().map(this::getInstanseResponse);
    }

    public Response<ServiceInstance> getInstanseResponse(List<ServiceInstance> instances) {
        if(instances.isEmpty()){
            return new EmptyResponse();
        }
        int size = instances.size();
        ServiceInstance serviceInstance = null;
        while(serviceInstance == null){
            if(this.instance_call_count < CALLS_PER_INSTANCE){
                serviceInstance =instances.get(this.instance_index);
                this.instance_call_count++;
            }
            else {
                this.instance_index++;
                this.instance_call_count=0;
                if(this.instance_index>=size){
                    this.instance_index=0;
                }
            }
        }



        return new DefaultResponse(serviceInstance);
    }
}
