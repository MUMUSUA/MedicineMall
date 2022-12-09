package com.example.mall.order.dao;

import com.example.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 19:29:05
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {

    void updateOrderStatus(String orderSn, Integer code, Integer payType);
}
