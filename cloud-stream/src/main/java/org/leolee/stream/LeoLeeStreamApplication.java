package org.leolee.stream;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.stream.Stream;

/**
 * 流式应用
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月23 14时40分
 */
@SpringBootApplication
public class LeoLeeStreamApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(LeoLeeStreamApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);


        Stream.of(1,2,3,34).map(v -> v.intValue()).forEach(v -> System.out.println(v));



    }


}
