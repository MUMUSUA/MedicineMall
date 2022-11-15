package com.example.mall.stock.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.stock.dao.StockOrderTaskDetailDao;
import com.example.mall.stock.entity.StockOrderTaskDetailEntity;
import com.example.mall.stock.service.StockOrderTaskDetailService;


@Service("stockOrderTaskDetailService")
public class StockOrderTaskDetailServiceImpl extends ServiceImpl<StockOrderTaskDetailDao, StockOrderTaskDetailEntity> implements StockOrderTaskDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StockOrderTaskDetailEntity> page = this.page(
                new Query<StockOrderTaskDetailEntity>().getPage(params),
                new QueryWrapper<StockOrderTaskDetailEntity>()
        );

        return new PageUtils(page);
    }

}