package com.example.mall.order.vo;

import lombok.Data;

/**
 * @description:
 * @author:wxl
 * @date:2022/11/18
 **/
@Data
public class PayVo {

    /**
     *商户订单号 必填
     */
    private String out_trade_no;

    /**
     *订单名称 必填
     */
    private String subject;

    /**
     *付款金额 必填
     */
    private String total_amount;

    /**
     *商品描述 可空
     */
    private String body;


}
