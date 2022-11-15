package com.example.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.stock.entity.StockSkuEntity;

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
}

