package com.robin.a;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;

@Configuration
public class LoadBalancerConfig {

	/**
	 * 	当注入 NacosRule 时，启动后第一次请求正常，第二次请求会出现404，主要原因是请求被路由到了非指定的服务上
	 * 	注释掉下面代码以后，使用默认的负载均衡规则，便恢复正常
	 * @return
	 */
    @Bean
    public IRule clusterLoadBalancerRule(){
        return new NacosRule();
    }

}