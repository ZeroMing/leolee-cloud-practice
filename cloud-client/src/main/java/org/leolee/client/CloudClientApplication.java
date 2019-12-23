package org.leolee.client;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月22 12时28分
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableCircuitBreaker
@EnableAspectJAutoProxy
@EnableFeignClients(basePackages = {"org.leolee.client.feign"})
public class CloudClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
