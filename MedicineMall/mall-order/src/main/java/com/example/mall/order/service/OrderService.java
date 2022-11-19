package com.example.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.order.entity.OrderEntity;
import com.example.mall.order.vo.OrderConfirmVo;

import java.util.Map;

/**
 * 订单
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 19:29:05
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

/**
 * 订单确认页返回需要用的数据
 * @return
 */
    OrderConfirmVo confirmOrder();

}

