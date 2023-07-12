package cn.wbomb.wxshop.controller;


import cn.wbomb.wxshop.api.OrderService;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {


    @Reference(version = "${wxshop.orderservice.version}", url = "wxshop.orderservice.url")
    private OrderService orderService;

    @GetMapping("/testRpc")
    public String testRpc() {
        orderService.placeOrder(1, 2);
        return "123";
    }
}
