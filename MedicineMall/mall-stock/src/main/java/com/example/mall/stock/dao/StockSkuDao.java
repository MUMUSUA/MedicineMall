package com.example.mall.stock.dao;

import com.example.mall.stock.entity.StockSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 商品库存
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@Mapper
public interface StockSkuDao extends BaseMapper<StockSkuEntity> {

    void addStock(@Param("skuId") Long skuId, @Param("wareId")Long wareId,@Param("skuNum") Integer skuNum);

    Long getSkuStock(Long item);
}
