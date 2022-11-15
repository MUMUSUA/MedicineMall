package com.example.mall.stock.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.stock.dao.StockSkuDao;
import com.example.mall.stock.entity.StockSkuEntity;
import com.example.mall.stock.service.StockSkuService;


@Service("stockSkuService")
public class StockSkuServiceImpl extends ServiceImpl<StockSkuDao, StockSkuEntity> implements StockSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockSkuEntity> page = this.page(
                new Query<StockSkuEntity>().getPage(params),
                new QueryWrapper<StockSkuEntity>()
        );

        return new PageUtils(page);
    }

}