package org.leolee.config.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月21 00时08分
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServerApplication.class,args);
    }


    @Bean
    public EnvironmentRepository leoleeEnvironmentRepository(){
        return (String application,String profile,String lable) ->{
            Environment environment = new Environment("default",profile);
            List<PropertySource> propertySources = environment.getPropertySources();
            Map<String,Object> source = new HashMap<>();
            source.put("name","LeoLee");
            PropertySource propertySource = new PropertySource("map",source);
            propertySources.add(propertySource);
            return environment;
        };
    }
}
