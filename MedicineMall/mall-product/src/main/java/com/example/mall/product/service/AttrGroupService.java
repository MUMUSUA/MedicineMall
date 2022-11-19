package com.example.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.product.entity.AttrGroupEntity;
import com.example.mall.product.vo.AttrGroupWithAttrsVo;
<<<<<<< HEAD
=======
import com.example.mall.product.vo.SpuItemAttrGroupVo;
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:16
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params, Long categoryId);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);
<<<<<<< HEAD
=======

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);


>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
}

