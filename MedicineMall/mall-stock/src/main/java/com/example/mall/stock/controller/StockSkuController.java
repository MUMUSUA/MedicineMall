package com.example.mall.stock.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.common.constant.ProductConstant;
import com.example.common.exception.NoStockException;
import com.example.mall.stock.vo.SkuHasStockVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.stock.entity.StockSkuEntity;
import com.example.mall.stock.service.StockSkuService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;
import com.example.mall.stock.entity.StockSkuEntity;
import com.example.mall.stock.service.StockSkuService;
import com.example.mall.stock.vo.SkuHasStockVo;
import com.example.mall.stock.vo.StockSkuLockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.example.common.exception.BizCodeEnum.NO_STOCK_EXCEPTION;

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
     * 锁定库存
     * @param vo
     *
     * 库存解锁的场景
     *      1）、下订单成功，订单过期没有支付被系统自动取消或者被用户手动取消，都要解锁库存
     *      2）、下订单成功，库存锁定成功，接下来的业务调用失败，导致订单回滚。之前锁定的库存就要自动解锁
     *      3）、
     *
     * @return
     */
    @PostMapping(value = "/lock/order")
    public R orderLockStock(@RequestBody StockSkuLockVo vo) {

        try {
            boolean lockStock = stockSkuService.orderLockStock(vo);
            return R.ok().setData(lockStock);
        } catch (NoStockException e) {
            return R.error(NO_STOCK_EXCEPTION.getCode(),NO_STOCK_EXCEPTION.getMessage());
        }
    }

    /**
     * 查询sku是否有库存
     * @return
     */
    @PostMapping(value = "/hasStock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds) {

        //skuId stock
        List<SkuHasStockVo> vos = stockSkuService.getSkuHasStock(skuIds);

        return R.ok().setData(vos);

    }



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
