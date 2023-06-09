package com.example.mall.product.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
<<<<<<< HEAD
 * @Created: with IntelliJ IDEA.
 * @author: 夏沫止水
 * @createTime: 2020-06-08 14:50
=======
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
 *
 * 二级分类id
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Catelog2Vo implements Serializable {

    /**
     * 一级父分类的id
     */
    private String catalog1Id;

    /**
     * 三级子分类
     */
    private List<Category3Vo> catalog3List;

    private String id;

    private String name;


    /**
     * 三级分类vo
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Category3Vo {

        /**
         * 父分类、二级分类id
         */
        private String catalog2Id;

        private String id;

        private String name;
    }

}
