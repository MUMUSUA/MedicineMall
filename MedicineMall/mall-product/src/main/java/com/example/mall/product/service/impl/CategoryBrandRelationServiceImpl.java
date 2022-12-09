package com.example.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mall.product.dao.BrandDao;
import com.example.mall.product.dao.CategoryDao;
import com.example.mall.product.entity.BrandEntity;
import com.example.mall.product.service.BrandService;
import com.example.mall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.product.dao.CategoryBrandRelationDao;
import com.example.mall.product.entity.CategoryBrandRelationEntity;
import com.example.mall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryDao categoryDao;
    @Autowired
    CategoryBrandRelationDao relationDao;
    @Autowired
    @Lazy
    BrandService brandService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long categoryId=categoryBrandRelation.getCatelogId();
        Long brandId=categoryBrandRelation.getBrandId();
        String categoryName=categoryDao.selectById(categoryId).getName();
        categoryBrandRelation.setCatelogName(categoryName);
        String brandName = brandDao.selectById(brandId).getName();
        categoryBrandRelation.setBrandName(brandName);
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelation=new CategoryBrandRelationEntity();
        categoryBrandRelation.setBrandId(brandId);
        categoryBrandRelation.setBrandName(name);
        this.update(categoryBrandRelation,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {

        this.baseMapper.updateCategory(catId,name);
    }



    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        List<CategoryBrandRelationEntity> catelogId = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));

        List<BrandEntity> collect = catelogId.stream().map(item -> {
            Long brandId = item.getBrandId();
            //查询品牌的详情
            BrandEntity byId = brandService.getById(brandId);
            return byId;
        }).collect(Collectors.toList());

        return collect;
//        return null;
    }

    @Override
    public List<BrandVo> getBrandsByCatId2(Long catId) {
        List<CategoryBrandRelationEntity> catelogId = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));

        List<BrandVo> collect = catelogId.stream().map(item -> {
            Long brandId = item.getBrandId();
            BrandVo vo=new BrandVo();
            //查询品牌的详情
           vo.setBrandId(brandService.getById(brandId).getBrandId());
           vo.setBrandName(brandService.getById(brandId).getName());
            return vo;
        }).collect(Collectors.toList());

        return collect;
//        return null;
    }

}