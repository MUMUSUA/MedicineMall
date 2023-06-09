package com.example.mall.seckill.feign;


import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient("mall-sale")
public interface CouponFeignService {

    /**
     * 查询最近三天需要参加秒杀商品的信息
     * @return
     */
    @GetMapping(value = "/sale/seckillsession/Lates3DaySession")
    R getLates3DaySession();

}
