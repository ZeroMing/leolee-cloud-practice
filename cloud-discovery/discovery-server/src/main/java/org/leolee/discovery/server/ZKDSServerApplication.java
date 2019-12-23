package org.leolee.discovery.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月21 18时11分
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZKDSServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZKDSServerApplication.class,args);
    }
}
