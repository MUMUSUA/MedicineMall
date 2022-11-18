package com.example.mall.stock.dao;

import com.example.mall.stock.entity.StockInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 仓库信息
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@Mapper
public interface StockInfoDao extends BaseMapper<StockInfoEntity> {
	
}
