package com.example.mall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mall.stock.entity.StockOrderTaskEntity;
import com.example.mall.stock.service.StockOrderTaskService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 库存工作单
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@RestController
@RequestMapping("stock/stockordertask")
public class StockOrderTaskController {
    @Autowired
    private StockOrderTaskService stockOrderTaskService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("stock:stockordertask:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockOrderTaskService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("stock:stockordertask:info")
    public R info(@PathVariable("id") Long id){
		StockOrderTaskEntity stockOrderTask = stockOrderTaskService.getById(id);

        return R.ok().put("stockOrderTask", stockOrderTask);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("stock:stockordertask:save")
    public R save(@RequestBody StockOrderTaskEntity stockOrderTask){
		stockOrderTaskService.save(stockOrderTask);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("stock:stockordertask:update")
    public R update(@RequestBody StockOrderTaskEntity stockOrderTask){
		stockOrderTaskService.updateById(stockOrderTask);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("stock:stockordertask:delete")
    public R delete(@RequestBody Long[] ids){
		stockOrderTaskService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
