package com.example.mall.user.feign;

import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("mall-order")
public interface OrderFeignService {
    @RequestMapping("/order/order/user/orders")
    public R userOrders();
}
