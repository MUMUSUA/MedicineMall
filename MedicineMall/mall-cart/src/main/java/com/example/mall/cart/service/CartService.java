package com.example.mall.cart.service;


import com.example.mall.cart.vo.CartItem;

import java.util.List;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 11:35
 * @Description:
 */
public interface CartService {


    List<CartItem> getUserCartItems();
}
