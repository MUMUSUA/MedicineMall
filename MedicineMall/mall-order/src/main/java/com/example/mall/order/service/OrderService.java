package com.example.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.to.mq.SeckillOrderTo;
import com.example.common.utils.PageUtils;
import com.example.mall.order.entity.OrderEntity;
import com.example.mall.order.vo.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException;

    PageUtils queryPageWithItem(Map<String, Object> params);

    OrderEntity getOrderByOrderSn(String orderSn);

    PayVo getOrderPay(String orderSn);

    SubmitOrderResponseVo submitOrder(OrderSubmitVo vo);

    void createSeckillOrder(SeckillOrderTo orderTo);

    void closeOrder(OrderEntity orderEntity);

    String handlePayResult(PayAsyncVo asyncVo);

    String asyncNotify(String notifyData);

    void PayOrder(OrderEntity order);
}

