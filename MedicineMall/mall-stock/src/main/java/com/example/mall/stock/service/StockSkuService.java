package com.example.mall.stock.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.to.OrderTo;
import com.example.common.to.mq.StockLockedTo;
import com.example.common.utils.PageUtils;
import com.example.mall.stock.entity.StockSkuEntity;
import com.example.mall.stock.vo.SkuHasStockVo;
import com.example.mall.stock.vo.StockSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
public interface StockSkuService extends IService<StockSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 添加库存
     * @param skuId
     * @param wareId
     * @param skuNum
     */
    void addStock(Long skuId, Long wareId, Integer skuNum);

    /**
     * 锁定库存
     * @param vo
     * @return
     */
    boolean orderLockStock(StockSkuLockVo vo);

    /**
     * 判断是否有库存
     * @param skuIds
     * @return
     */
    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    /**
     * 解锁库存
     * @param to
     */
    void unlockStock(StockLockedTo to);

    /**
     * 解锁订单
     * @param orderTo
     */
    void unlockStock(OrderTo orderTo);
}


