package cn.wbomb.wxshop.mock;



import cn.wbomb.api.rpc.OrderService;

import org.apache.dubbo.config.annotation.Service;

@Service(version = "${wxshop.orderservice.version}")
public class MockOrderService implements OrderService {
    @Override
    public String sysHello(String name) {
        return null;
    }
}
