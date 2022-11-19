package com.example.mall.sale.dao;

import com.example.mall.sale.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 20:25:11
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
