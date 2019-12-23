package org.leolee.discovery.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月21 18时15分
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableScheduling
public class ZKDSClientApplication {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Value("${spring.application.name}")
    private String applicationName;

    private Set<String> targetUrls = new HashSet<>();

    @Scheduled(fixedRate = 5 * 1000)
    public void updateTargetUrls(){
        Set<String> oldTargetUrls = this.targetUrls;
        List<ServiceInstance> instances = discoveryClient.getInstances(applicationName);
        Set<String> newTargetUrls = instances.stream().map(s -> {
            return s.isSecure() ? "https://" + s.getHost() + ":" + s.getPort():
                    "http://" +s.getHost() + ":" + s.getPort();
        }).collect(Collectors.toSet());
        this.targetUrls = newTargetUrls;
        oldTargetUrls.clear();
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ZKDSClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
        // SpringApplication.run(ZKDSClientApplication.class,args);
    }

    @GetMapping("/home")
    public String home(@RequestParam String message) {
        System.out.println("接收到:" + message);
        return "Hello world";
    }

    @GetMapping("/invoke/home")
    public String invokeHome(@RequestParam String message) {
        // 获取服务器列表
        List<String> urls = new ArrayList<>(this.targetUrls);
        // 轮询列表
        int size = urls.size();
        // 选择其中一台服务器
        int index = new Random().nextInt(size);
        // RestTemplate发送请求
        String tarhetUrl = urls.get(index);
        // 输出响应
        return restTemplate.getForObject(tarhetUrl+"/home?message="+message,String.class);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
