package com.example.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.common.constant.ProductConstant;
import com.example.mall.product.dao.AttrAttrgroupRelationDao;
import com.example.mall.product.dao.AttrGroupDao;
import com.example.mall.product.dao.CategoryDao;
import com.example.mall.product.entity.AttrAttrgroupRelationEntity;
import com.example.mall.product.entity.AttrGroupEntity;
import com.example.mall.product.entity.CategoryEntity;
import com.example.mall.product.service.CategoryService;
import com.example.mall.product.vo.AttrGroupRelationVo;
import com.example.mall.product.vo.AttrRespVo;
import com.example.mall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.product.dao.AttrDao;
import com.example.mall.product.entity.AttrEntity;
import com.example.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //保存基本数据
        this.save(attrEntity);
        //保存关联信息
        if(attr.getAttrType()==1 && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity=new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(attrAttrgroupRelationEntity);
        }


    }

//    @Override
//    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
//        System.out.println("选择显示的类型是："+type);
//        QueryWrapper<AttrEntity> queryWrapper=new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?1:0);
//        if(catelogId !=0){
//            queryWrapper.eq("catelog_id",catelogId);
//        }
//        String key=(String)params.get("key");
//        if(!StringUtils.isEmpty(key)){
//            queryWrapper.and((wrapper)->{
//                wrapper.eq("attr_id",key).or().like("attr_name",key);
//            });
//        }
//
//        IPage<AttrEntity> page=this.page(
//                new Query<AttrEntity>().getPage(params),queryWrapper);
//        PageUtils pageUtils=new PageUtils(page);
//        List<AttrEntity> records=page.getRecords();
//        List<AttrRespVo> respVoList = records.stream().map((attrEntity -> {
//            AttrRespVo attrRespVo = new AttrRespVo();
//            BeanUtils.copyProperties(attrEntity, attrRespVo);
//
//            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
//                    .eq("attr_id", attrEntity.getAttrId()));
//          //if (attrAttrgroupRelationEntity.getAttrId()!=null &&attrAttrgroupRelationEntity.getAttrGroupId()!=null) {
//            if (attrAttrgroupRelationEntity != null) {
//                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrAttrgroupRelationEntity.getAttrGroupId());
//                if(attrGroupEntity!=null) {
//                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
//                }
//            }
//
//            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
//            if (categoryEntity != null) {
//
//                attrRespVo.setCatelogName(categoryEntity.getName());
//            }
//
//            return attrRespVo;
//
//        })).collect(Collectors.toList());
//        pageUtils.setList(respVoList);
//        return pageUtils;
//    }
//


    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {

        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type","base".equalsIgnoreCase(attrType) ?
                        ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() : ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        //根据catelogId查询信息
        if (catelogId != 0) {
            queryWrapper.eq("catelog_id",catelogId);
        }

        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            //attr_id attr_name
            queryWrapper.and((wrapper) -> {
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();

        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //设置分类和分组的名字
            if ("base".equalsIgnoreCase(attrType)) {
//                AttrAttrgroupRelationEntity relationEntity =
//                        attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
                            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attrEntity.getAttrId()));
                if (relationEntity != null && relationEntity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }

            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());

            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;

    }


    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo respVo=new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,respVo);
        //分组信息
        if(attrEntity.getAttrType()==1) {
            AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attrId));
            if (relationEntity != null) {
                respVo.setAttrGroupId(relationEntity.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                if (attrGroupEntity != null) {
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }
        }
        //分类信息
        Long catelog_id=attrEntity.getCatelogId();
        respVo.setCatelogPath(categoryService.findCatelogId(catelog_id));
        CategoryEntity category=categoryDao.selectById(catelog_id);
        if(category!=null){
            respVo.setCatelogName(category.getName());
        }


        return respVo;
    }

    @Override
    public void updateAttrResp(AttrRespVo attr) {
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        //修改分组关联
        if(attrEntity.getAttrType()==1){
        AttrAttrgroupRelationEntity relationEntity=new AttrAttrgroupRelationEntity();
        relationEntity.setAttrGroupId(attr.getAttrGroupId());
        relationEntity.setAttrId(attr.getAttrId());
        attrAttrgroupRelationDao.update(relationEntity,new UpdateWrapper<AttrAttrgroupRelationEntity>()
                .eq("attr_id",attr.getAttrId()));
                                        }
    }

    /**
     * 根据分组id找到关联的所有属性
     * @param attrgroupId
     * @return
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {

        List<AttrAttrgroupRelationEntity> entities = attrAttrgroupRelationDao.selectList
                (new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrgroupId));

        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        //根据attrIds查找所有的属性信息
        //Collection<AttrEntity> attrEntities = this.listByIds(attrIds);

        //如果attrIds为空就直接返回一个null值出去
        if (attrIds == null || attrIds.size() == 0) {
            return null;
        }

        List<AttrEntity> attrEntityList = this.baseMapper.selectBatchIds(attrIds);

        return attrEntityList;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {

        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());

        attrAttrgroupRelationDao.deleteBatchRelation(entities);
    }

    /**
     * 获取当前分组没有被关联的所有属性
     * @param params
     * @param attrgroupId
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {

        //1、当前分组只能关联自己所属的分类里面的所有属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        //获取当前分类的id
        Long catelogId = attrGroupEntity.getCatelogId();

        //2、当前分组只能关联别的分组没有引用的属性
        //2.1）、当前分类下的其它分组
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                .eq("catelog_id", catelogId));

        //获取到所有的attrGroupId
        List<Long> collect = groupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());


        //2.2）、这些分组关联的属性
        List<AttrAttrgroupRelationEntity> groupId = attrAttrgroupRelationDao.selectList
                (new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));

        List<Long> attrIds = groupId.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());

        //2.3）、从当前分类的所有属性移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());

        if (attrIds != null && attrIds.size() > 0) {
            queryWrapper.notIn("attr_id", attrIds);
        }

        //判断是否有参数进行模糊查询
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            queryWrapper.and((w) -> {
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);

        PageUtils pageUtils = new PageUtils(page);


        return pageUtils;
    }


}