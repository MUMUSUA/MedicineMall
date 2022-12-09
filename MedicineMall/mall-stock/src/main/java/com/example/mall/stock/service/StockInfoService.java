package com.example.mall.stock.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.stock.entity.StockInfoEntity;
import com.example.mall.stock.vo.FareVo;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
public interface StockInfoService extends IService<StockInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

