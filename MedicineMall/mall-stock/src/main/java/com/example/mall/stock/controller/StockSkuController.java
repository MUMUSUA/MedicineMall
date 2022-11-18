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

import com.example.mall.stock.entity.StockSkuEntity;
import com.example.mall.stock.service.StockSkuService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 商品库存
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@RestController
@RequestMapping("stock/stocksku")
public class StockSkuController {
    @Autowired
    private StockSkuService stockSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("stock:stocksku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("stock:stocksku:info")
    public R info(@PathVariable("id") Long id){
		StockSkuEntity stockSku = stockSkuService.getById(id);

        return R.ok().put("stockSku", stockSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("stock:stocksku:save")
    public R save(@RequestBody StockSkuEntity stockSku){
		stockSkuService.save(stockSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("stock:stocksku:update")
    public R update(@RequestBody StockSkuEntity stockSku){
		stockSkuService.updateById(stockSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("stock:stocksku:delete")
    public R delete(@RequestBody Long[] ids){
		stockSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
