package cn.wbomb.order.service;

import cn.wbomb.api.rpc.OrderService;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.orderservice.version}")
public class RpcOrderService implements OrderService {

    @Override
    public String sysHello(String name) {
        return "Hello, " + name;
    }
}
