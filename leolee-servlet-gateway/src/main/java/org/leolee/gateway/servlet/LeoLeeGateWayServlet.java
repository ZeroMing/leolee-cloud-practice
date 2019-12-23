package org.leolee.gateway.servlet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * LeoLeeServlet网关处理器
 * @author: zeroming@163.com
 * @version:
 * @date: 2019年12月23 11时16分
 */
@WebServlet(name = "leoLeeGateWayServlet",urlPatterns = "/gateway/*")
public class LeoLeeGateWayServlet extends HttpServlet {


    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        // 解析请求地址
        String pathInfo = req.getPathInfo();
        String[] pathArray = StringUtils.split(pathInfo.substring(1), "/");
        String serviceName = pathArray[0];
        String apiPath = "/" +pathArray[1];
        // 服务实例名称
        List<ServiceInstance> instances = discoveryClient.getInstances(serviceName);
        // 选择一台实例
        // 拼接请求URL
        // 匹配路由


        // 创建转发客户端


        // 转发

        super.service(req, resp);
    }

}
