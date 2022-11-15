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

import com.example.mall.stock.entity.StockOrderTaskDetailEntity;
import com.example.mall.stock.service.StockOrderTaskDetailService;
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
@RequestMapping("stock/stockordertaskdetail")
public class StockOrderTaskDetailController {
    @Autowired
    private StockOrderTaskDetailService stockOrderTaskDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("stock:stockordertaskdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockOrderTaskDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("stock:stockordertaskdetail:info")
    public R info(@PathVariable("id") Long id){
		StockOrderTaskDetailEntity stockOrderTaskDetail = stockOrderTaskDetailService.getById(id);

        return R.ok().put("stockOrderTaskDetail", stockOrderTaskDetail);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("stock:stockordertaskdetail:save")
    public R save(@RequestBody StockOrderTaskDetailEntity stockOrderTaskDetail){
		stockOrderTaskDetailService.save(stockOrderTaskDetail);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("stock:stockordertaskdetail:update")
    public R update(@RequestBody StockOrderTaskDetailEntity stockOrderTaskDetail){
		stockOrderTaskDetailService.updateById(stockOrderTaskDetail);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("stock:stockordertaskdetail:delete")
    public R delete(@RequestBody Long[] ids){
		stockOrderTaskDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
