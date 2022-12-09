package com.example.mall.stock.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.mall.stock.vo.FareVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.stock.entity.StockInfoEntity;
import com.example.mall.stock.service.StockInfoService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 仓库信息
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-15 21:29:22
 */
@RestController
@RequestMapping("stock/stockinfo")
public class StockInfoController {
    @Autowired
    private StockInfoService stockInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("stock:stockinfo:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = stockInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("stock:stockinfo:info")
    public R info(@PathVariable("id") Long id){
		StockInfoEntity stockInfo = stockInfoService.getById(id);

        return R.ok().put("stockInfo", stockInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("stock:stockinfo:save")
    public R save(@RequestBody StockInfoEntity stockInfo){
		stockInfoService.save(stockInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("stock:stockinfo:update")
    public R update(@RequestBody StockInfoEntity stockInfo){
		stockInfoService.updateById(stockInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("stock:stockinfo:delete")
    public R delete(@RequestBody Long[] ids){
		stockInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 获取运费信息
     * @return
     */
    @GetMapping(value = "/fare")
    public R getFare(@RequestParam("addrId") Long addrId) {

        FareVo fare = stockInfoService.getFare(addrId);

        return R.ok().setData(fare);
    }
}
