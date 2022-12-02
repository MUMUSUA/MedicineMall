package com.example.mall.order.feign;

import com.example.common.utils.R;
import com.example.mall.stock.vo.StockSkuLockVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @description:
 * @author:wxl
 * @date:2022/12/01
 **/
@FeignClient("gulimall-stock")
public interface WmsFeignService {

    /**
     * 查询sku是否有库存
     * @return
     */
    @PostMapping(value = "stock/stocksku/hasStock")
    public R getSkuHasStock(@RequestBody List<Long> skuIds);


    /**
     * 查询运费和收货地址信息
     * @param addrId
     * @return
     */
    @GetMapping(value = "/stock/stockinfo/fare")
    public R getFare(@RequestParam("addrId") Long addrId);


    /**
     * 锁定库存
     * @param vo
     * @return
     */
    @PostMapping(value = "/stock/stocksku/lock/order")
    public R orderLockStock(@RequestBody StockSkuLockVo vo);
}
