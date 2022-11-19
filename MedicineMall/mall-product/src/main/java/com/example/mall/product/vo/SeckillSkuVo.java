package com.example.mall.product.vo;

import lombok.Data;

import java.math.BigDecimal;

<<<<<<< HEAD
/**
 * @Description:
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-07-10 15:57
 **/
=======
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36

@Data
public class SeckillSkuVo {

    /**
     * 活动id
     */
    private Long promotionId;
    /**
     * 活动场次id
     */
    private Long promotionSessionId;
    /**
     * 商品id
     */
    private Long skuId;
    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;
    /**
     * 秒杀总量
     */
    private Integer seckillCount;
    /**
     * 每人限购数量
     */
    private Integer seckillLimit;
    /**
     * 排序
     */
    private Integer seckillSort;

    //当前商品秒杀的开始时间
    private Long startTime;

    //当前商品秒杀的结束时间
    private Long endTime;

    //当前商品秒杀的随机码
    private String randomCode;

}
