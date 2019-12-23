package org.leolee.client.hystrix;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.RunAs;
import java.lang.annotation.*;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月22 22时27分
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CircuitBreakerSeamphore {
    int value() default 0;
}
