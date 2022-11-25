package com.example.mall.cart.service;

import com.example.mall.cart.vo.Cart;
import com.example.mall.cart.vo.CartItem;

import java.util.concurrent.ExecutionException;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 11:35
 * @Description:
 */
public interface CartService {

    CartItem addToCart(Long skuId, Integer num) throws ExecutionException, InterruptedException;
    CartItem getCartItem(Long skuId);

    /**
     * 获取整个购物车
     * @return
     */
    Cart getCart() throws ExecutionException, InterruptedException;

    /**
     * 清空购物车
     * @param
     */
     public  void clearCart(String catKey);
}
