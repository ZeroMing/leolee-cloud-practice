package org.leolee.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月23 00时00分
 */
@FeignClient(name = "${spring.application.name}")
public interface OrderService {

    @GetMapping("/order/{orderId}")
    public String getOrder(@PathVariable String orderId);

}
