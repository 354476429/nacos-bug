package com.robin.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class LoadBalancerConfig {

    @Bean
    public IRule clusterLoadBalancerRule(){
        return new NacosRule();
    }

}