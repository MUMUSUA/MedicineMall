package com.example.mall.product.service.impl;


import com.example.mall.product.entity.AttrEntity;
import com.example.mall.product.service.AttrService;
import com.example.mall.product.vo.AttrGroupWithAttrsVo;
<<<<<<< HEAD
=======
import com.example.mall.product.vo.SpuItemAttrGroupVo;
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.product.dao.AttrGroupDao;
import com.example.mall.product.entity.AttrGroupEntity;
import com.example.mall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {
    @Autowired
    AttrService attrService;
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
        String key=(String)params.get("key");
        //select * from attrgroup where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
        QueryWrapper<AttrGroupEntity> wrapper=new QueryWrapper<AttrGroupEntity>();
        if(! StringUtils.isEmpty(key)){
            wrapper.and((obj)->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
            if(categoryId==0) {
                IPage<AttrGroupEntity> page = this.page(
                        new Query<AttrGroupEntity>().getPage(params),
                        wrapper
                );
                return new PageUtils(page);
            }else{

                IPage<AttrGroupEntity> page = this.page(
                        new Query<AttrGroupEntity>().getPage(params),
                        wrapper.eq("catelog_id",categoryId)
                );
                return new PageUtils(page);
            }


    }

    /**
     * 根据分类id查询出所有的分组以及这些组里面的属性
     * @param catelogId
     * @return
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId) {

        //1、查询分组信息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        //2、查询所有属性
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group,attrGroupWithAttrsVo);

            List<AttrEntity> attrs = attrService.getRelationAttr(attrGroupWithAttrsVo.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(attrs);

            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());

        return collect;
    }


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }
<<<<<<< HEAD
=======


    @Override
    public List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {

        //1、查出当前spu对应的所有属性的分组信息以及当前分组下的所有属性对应的值
        AttrGroupDao baseMapper = this.getBaseMapper();
        List<SpuItemAttrGroupVo> vos = baseMapper.getAttrGroupWithAttrsBySpuId(spuId,catalogId);

        return vos;
    }
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
}