package com.example.mall.product.service.impl;

import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
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

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long categoryId) {
            if(categoryId==0) {
                IPage<AttrGroupEntity> page = this.page(
                        new Query<AttrGroupEntity>().getPage(params),
                        new QueryWrapper<AttrGroupEntity>()
                );
                return new PageUtils(page);
            }else{
                String key=(String)params.get("key");
                //select * from attrgroup where catelog_id=? and (attr_group_id=key or attr_group_name like %key%)
                QueryWrapper<AttrGroupEntity> wrapper=new QueryWrapper<AttrGroupEntity>().eq("catelog_id",categoryId);
                if(! StringUtils.isNullOrEmpty(key)){
                    wrapper.and((obj)->{
                        obj.eq("attr_group_id",key).or().like("attr_group_name",key);
                    });
                }
                IPage<AttrGroupEntity> page = this.page(
                        new Query<AttrGroupEntity>().getPage(params),
                        wrapper
                );
                return new PageUtils(page);
            }


    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }
}