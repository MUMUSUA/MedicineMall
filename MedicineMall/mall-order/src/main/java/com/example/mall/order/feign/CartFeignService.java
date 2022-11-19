package com.example.mall.order.feign;

import com.example.mall.order.vo.OrderItemVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @description:
 * @author:wxl
 * @date:2022/11/19
 **/
@FeignClient("mall-cart")
public interface CartFeignService {

    @GetMapping("/currentUserCartItems")
    public List<OrderItemVo> getCurrentUserCartItems();

}
