package com.example.mall.order.feign;

import com.example.common.utils.R;
import com.example.mall.order.vo.WareSkuLockVo;
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
@FeignClient("mall-stock")
public interface WmsFeignService {

    /**
     * 查询sku是否有库存
     * @return
     */
    @PostMapping(value = "stock/stocksku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);


    /**
     * 查询运费和收货地址信息
     * @param addrId
     * @return
     */
    @GetMapping(value = "/stock/stockinfo/fare")
    R getFare(@RequestParam("addrId") Long addrId);


    /**
     * 锁定库存
     * @param vo
     * @return
     */
    @PostMapping(value = "/stock/stocksku/lock/order")
    R orderLockStock(@RequestBody WareSkuLockVo vo);
}
