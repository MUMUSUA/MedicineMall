package com.example.mallcart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 */
/**
 * @Description: 整个购物车存放的商品信息   需要计算的属性需要重写get方法，保证每次获取属性都会进行计算
 * @Created: with IntelliJ IDEA.
 * @author: marui
 * @createTime: 2020-11-07 14:10
 **/
public class Cart {
    /**
     * 购物车子项信息
     */
    private List<CartItem> items;
    /**
     * 商品数量
     */
    private Integer countNum;

    /**
     * 商品类型数量
     */
    private Integer countType;
    /**
     * 商品总价
     */
    private BigDecimal totalAmount;
    /**
     * 减免价格
     */
    private BigDecimal reduce = new BigDecimal("0.00");

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                countNum += item.getCount();
            }
        }
        return count;
    }

    public int getCountType() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem item : items) {
                countNum += 1;
            }
        }
        return count;
    }


    public BigDecimal getTotalAmount() {
        //计算购物项总价
        BigDecimal amount = new BigDecimal("0");
         if (items != null && items.size() > 0) {
            for (CartItem item : items) {
           BigDecimal totalPrice = item.getTotalPrice();
                amount = amount.add(totalPrice);//商品总价+当前商品的总价
            }
        }
         //减掉优惠的总价
        BigDecimal subtract = amount.subtract(getReduce());
         return amount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

}
