一个演示nacos bug的demo 

该bug涉及到的类为 com.alibaba.cloud.nacos.ribbon.NacosRule 

该bug表现为 

使用nacos作为注册中心并且使用NacosRule负载均衡策略时 

a服务中的a接口 按顺序依次调用 

b服务的b接口 

c服务的c接口 

d服务的d接口 

通过网关访问a服务的a接口时，第一次正常访问，全部畅通 

但第二次请求a服务的a接口时，a接口中访问b服务的b接口出现404，请求被路由到了d服务导致404 

后面继续请求依然404 当不使用NacosRule规则时，则不出现404问题

通过调试发现出问题根本原因是NacosRule 父类 com.netflix.loadbalancer.AbstractLoadBalancerRule.lb 的这个变量引起 

在启动后的第一次请求时，b,c,d的请求可以正常负载均衡到各个服务，但是在第一次请求之后，发现保存有b服务的负载均衡器-com.netflix.loadbalancer.ZoneAwareLoadBalancer 父类中 com.netflix.loadbalancer.BaseLoadBalancer.rule 该字段保存的是NacosRule，但是里面保存的并不是b服务的相关信息， 而是d服务的相关信息，导致了第二次请求b服务时被路由请求到了d服务，导致404报错



spring boot版本 2.2.9.RELEASE 

nacos-client版本 spring-cloud-starter-alibaba-nacos-discovery 2.2.1.RELEASE 

nacos-server版本 1.3.2

问题复现可检出代码本地测试 

启动方式： 修改bootstrap.yml中的spring.cloud.naocs.discovery.server-addr 为nacos-server地址 

分别启动gateway，a，b，c，d服务 通过POST请求 http://localhost/a/a/test 两次即可复现问题
