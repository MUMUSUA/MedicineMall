package com.example.mall.product.dao;

import com.example.mall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.mall.product.vo.SkuItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:15
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<String> getSkuSaleAttrValuesAsStringList(Long skuId);

    List<SkuItemSaleAttrVo> getSaleAttrBySpuId(Long spuId);
}
