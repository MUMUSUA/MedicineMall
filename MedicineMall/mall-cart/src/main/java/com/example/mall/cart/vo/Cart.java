package com.example.mall.cart.vo;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 9:22
 * @Description:
 */

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 */
public class Cart {
    List<CartItem> items;//购物项
    private Integer countNum;//商品数量
    private Integer countType; //商品类型数量
    private BigDecimal totalAmount; //商品总价
    private BigDecimal reduce = new BigDecimal("0.00");//减免价格

    public List<CartItem> getItems() {
        return items;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public Integer getCountNum() {
        int count=0;
        if(items!=null && items.size()>0);
        for (CartItem item : items) {
            count += item.getCount();
        }
        return count;
    }

    public Integer getCountType() {
        int count=0;
        if(items!=null && items.size()>0) {
            for (CartItem item : items) {
                count += 1;
            }
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0");
        if (items != null && items.size() > 0)
        {
            for (CartItem item : items) {
                if (item.getCheck()) {
                    amount = amount.add(item.getTotalPrice());
                }
            }
        }
        //减免价格
        BigDecimal subtract = amount.subtract(getReduce());
        return subtract;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
