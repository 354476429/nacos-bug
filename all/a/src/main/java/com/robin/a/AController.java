package com.robin.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AController {

	@Autowired
	RestTemplate restTemplate;

	@PostMapping("/a/test")
	public String c() {
//		使用NacosRule 时第一次请求是正常的，b,c,d服务都正常响应，第二次请求时，请求b服务出现404，原因是被路由到了最后请求的d服务上，
//		如果把请求d服务的代码注释，重启之后，第一次请求依然正常，但是第二次请求也依然是404，原因是被路由到了c服务上
//		通过调试发现出问题根本原因是 com.netflix.loadbalancer.AbstractLoadBalancerRule.lb 的这个变量引起的
//		在启动后的第一次请求时，b,c,d的请求可以正常负载均衡到各个服务，但是在第一次请求之后，发现保存有b服务的负载均衡器-com.netflix.loadbalancer.ZoneAwareLoadBalancer<T>
//		父类中 com.netflix.loadbalancer.BaseLoadBalancer.rule 
//		该字段保存的是NacosRule，并且里面保存的并不是b服务的相关信息，而是d服务的相关信息，导致了第二次请求b服务时被路由请求到了d服务，导致404报错
//		当注释掉请求d服务代码，重启之后第二次请求也是404，但是是被路由到c服务上去了
//		该问题可以大致描述为使用NacosRule作为负载均衡规则并且在a接口内请求多个其他服务接口，那么在a接口中的第一个请求其他服务接口的非第一次请求将会被路由到最后一个接口所在服务，导致404
		String b = restTemplate.postForObject("http://b/b/test", new LinkedMultiValueMap<>(), String.class);
		System.out.println(b);
		String c = restTemplate.postForObject("http://c/c/test", new LinkedMultiValueMap<>(), String.class);
		System.out.println(c);
		
		String d = restTemplate.postForObject("http://d/d/test", new LinkedMultiValueMap<>(), String.class);
		System.out.println(d);
		return "aaaaaa";
	}

}
