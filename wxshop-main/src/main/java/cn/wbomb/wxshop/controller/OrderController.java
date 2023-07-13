package cn.wbomb.wxshop.controller;

import cn.wbomb.api.data.OrderInfo;
import cn.wbomb.wxshop.entity.OrderResponse;
import cn.wbomb.wxshop.entity.Response;
import cn.wbomb.wxshop.service.OrderService;
import cn.wbomb.wxshop.service.UserContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public Response<OrderResponse> createOrder(@RequestBody OrderInfo orderInfo) {
            orderService.deductStock(orderInfo);
            return Response.of(orderService.createOrder(orderInfo, UserContext.getCurrentUser().getId()));
    }
}
