package com.example.mall.product.feign;

import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("mall-stock")
public interface StockFeignService {

    @PostMapping(value = "/stock/stocksku/hasStock")
    R getSkuHasStock(@RequestBody List<Long> skuIds);

}