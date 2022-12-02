package com.example.mall.order.vo;

import lombok.Data;

/**
 * @description:
 * @author:wxl
 * @date:2022/12/02
 **/
@Data
public class SkuStockVo {
    private Long skuId;

    private Boolean hasStock;
}
