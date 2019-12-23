package org.leolee.gateway;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月23 11时13分
 */
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan(basePackageClasses = org.leolee.gateway.servlet.LeoLeeGateWayServlet.class)
public class LeoLeeServletGateWayApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LeoLeeServletGateWayApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
