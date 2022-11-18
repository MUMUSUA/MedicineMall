package com.example.mall.stock.dao;

import com.example.mall.stock.entity.StockOrderTaskEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存工作单
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@Mapper
public interface StockOrderTaskDao extends BaseMapper<StockOrderTaskEntity> {
	
}
