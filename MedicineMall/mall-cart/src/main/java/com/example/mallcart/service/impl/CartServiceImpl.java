package com.example.mallcart.service.impl;

import com.example.mallcart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: marui
 * @Date: 2022/11/7
 * @Time: 15:54
 * @Description:
 */
@Service
@Slf4j
public class CartServiceImpl implements CartService {
    @Resource
    private StringRedisTemplate redisTemplate;
}
