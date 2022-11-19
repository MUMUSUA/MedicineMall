package com.example.mall.cart.service.impl;


import com.alibaba.nacos.shaded.org.checkerframework.checker.units.qual.A;
import com.example.mall.cart.To.UserInfoTo;
import com.example.mall.cart.feign.ProductFeignService;
import com.example.mall.cart.interceptor.CartInterceptor;
import com.example.mall.cart.service.CartService;
import com.example.mall.cart.vo.CartItem;
import com.example.mall.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import static com.example.common.constant.CartConstant.CART_PREFIX;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 11:37
 * @Description:
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    ProductFeignService productFeignService;




    private List<CartItem> getCartItems(String cartKey) {
        return null;
    }

    @Override
    public List<CartItem> getUserCartItems() {
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (userInfoTo.getUserId() == null) {
            return null;
        } else {
            String cartKey = CART_PREFIX + userInfoTo.getUserId();
            List<CartItem> cartItems = getCartItems(cartKey);
            //获取所有被選中的購物項，先渦濾，再收集返回到collect
            List<CartItem> collect = cartItems.stream()
                    .filter(item -> item.getCheck())
                    .map(item ->{
                        //根据不同的id给每一个商品找价格
                        BigDecimal price = productFeignService.getPrice(item.getSkuId());
                    //TODO 查询商品服务，更新为最新价格
                        item.setPrice(price);
                        return item;
                    })
                    .collect(Collectors.toList());
            return collect;
        }
        /**
         *暂时占位return null
         */
//        return null;
    }





}
