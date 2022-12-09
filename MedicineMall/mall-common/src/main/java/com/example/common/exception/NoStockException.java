package com.example.common.exception;

import lombok.Getter;
import lombok.Setter;



public class NoStockException extends RuntimeException {

    @Getter @Setter
    private Long skuId;

//    public NoStockException(Long skuId) {
//        super("商品id："+ skuId + "库存不足！");
//    }

    public NoStockException(String msg) {
        super(msg);
    }
//    public Long setSkuId(){return  skuId;}
//    public void setSkuId(Long skuId){
//        this.skuId=skuId;
//    }


}
