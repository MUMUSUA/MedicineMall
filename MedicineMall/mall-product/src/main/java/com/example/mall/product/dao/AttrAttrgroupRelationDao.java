package com.example.mall.product.dao;

import com.example.mall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:16
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelation(List<AttrAttrgroupRelationEntity> entities);
}
