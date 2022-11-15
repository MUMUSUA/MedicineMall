package com.example.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.stock.entity.StockOrderTaskDetailEntity;

import java.util.Map;

/**
 * 库存工作单
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
public interface StockOrderTaskDetailService extends IService<StockOrderTaskDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

