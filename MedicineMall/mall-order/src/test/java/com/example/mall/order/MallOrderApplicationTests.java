package com.example.mall.order;

import com.example.mall.order.entity.OrderEntity;
import com.example.mall.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MallOrderApplicationTests {

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
        List<OrderEntity> list=orderService.list();
OrderEntity order=new OrderEntity();
order.setId(2L);
order.setOrderSn("11111");
orderService.save(order);
        System.out.println("添加成功");
    }

}
