package org.leolee.server;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月21 20时33分
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = {"org.leolee.server.feign"})
public class CloudServerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(CloudServerApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }


    @GetMapping("order/{orderId}")
    public String getOrder(@PathVariable String orderId){
        return "server-order"+orderId;
    }
}
