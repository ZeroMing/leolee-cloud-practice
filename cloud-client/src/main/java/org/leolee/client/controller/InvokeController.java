package org.leolee.client.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.leolee.client.feign.OrderService;
import org.leolee.client.hystrix.CircuitBreakerSeamphore;
import org.omg.PortableServer.THREAD_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月22 12时29分
 */
@RestController
public class InvokeController {

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private OrderService orderService;


    private Map<String,Set<String>> targetUrls = new ConcurrentHashMap<>();

    @Scheduled(fixedRate = 5 * 1000)
    public void updateTargetUrls(){
        Map<String,Set<String>> oldTargetUrls = this.targetUrls;
        List<String> services = discoveryClient.getServices();
        Map<String,Set<String>> newTargetUrls =  new ConcurrentHashMap<>();
        services.stream().forEach(service -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            Set<String> newTargetUrlSet = instances.stream().map(s -> {
                return s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort():
                        "http://" +s.getHost() + ":" + s.getPort();
            }).collect(Collectors.toSet());
            newTargetUrls.put(service,newTargetUrlSet);
        });
        this.targetUrls = newTargetUrls;
        oldTargetUrls.clear();
    }

    @GetMapping("/invoke/{serviceName}/home")
    public String invokeService(@PathVariable String serviceName, @RequestParam String message){
        // 获取服务器列表
        List<String> urls = new ArrayList<>(this.targetUrls.get(serviceName));
        // 轮询列表
        int size = urls.size();
        // 选择其中一台服务器
        int index = new Random().nextInt(size);
        // RestTemplate发送请求
        String tarhetUrl = urls.get(index);
        // 输出响应
        return restTemplate.getForObject(tarhetUrl+"/home?message="+message,String.class);
    }



    @HystrixCommand(fallbackMethod = "defaultStores",
            commandProperties = {
//                    // 信号量隔离
                    // @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
                    @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    @GetMapping("/hystrix")
    public String hystrix() throws InterruptedException {
        Thread.sleep(20*1000);
        System.out.println("继续执行主线程");
        return "hystrix";
    }

    /**
     * 熔断方法与被熔断方法需要保持相同的方法签名
     * @return
     */
    public String defaultStores() {
        return "error";
    }



    @CircuitBreakerSeamphore(value = 2)
    @GetMapping("/seamphore1")
    public String hystrixSeamphore () throws InterruptedException {
        System.out.println("hystrixSeamphore继续执行主线程");
        return "seamphore1";
    }

    @CircuitBreakerSeamphore(value = 3)
    @GetMapping("/seamphore2")
    public String hystrixSeamphore2 () throws InterruptedException {
        System.out.println("hystrixSeamphore继续执行主线程");
        return "seamphore2";
    }

    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable String orderId){
        return "server-order"+orderId;
    }

    @GetMapping("/feign/order/{orderId}")
    public String getFeignOrder(@PathVariable String orderId){
        return orderService.getOrder(orderId) + "feign";
    }



}
