package cn.wbomb.wxshop.controller;

import cn.wbomb.api.rpc.OrderService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Reference(version = "${wxshop.orderservice.version}")
    private OrderService orderService;

    @GetMapping("/testRpc")
    public String testRpc() {
        return orderService.sysHello("George");
//        return "123";
    }
}
