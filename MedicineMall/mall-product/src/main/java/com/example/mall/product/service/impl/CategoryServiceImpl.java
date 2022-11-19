package com.example.mall.product.service.impl;

import com.example.mall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.product.dao.CategoryDao;
import com.example.mall.product.entity.CategoryEntity;
import com.example.mall.product.service.CategoryService;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        //查出所有分类
        List<CategoryEntity> categoryEntityList = baseMapper.selectList(null);
        //组装成树形结构
        //一级分类
        List<CategoryEntity> level = categoryEntityList.stream().filter(categoryEntity ->
                categoryEntity.getParentCid() == 0
        ).map((menu)->{
                    menu.setChildren(getChildrens(menu,categoryEntityList));
                    return menu;
        }).sorted((menu1,menu2)->{
                    return (menu1.getSort() == null ?0: menu1.getSort())-(menu2.getSort()==null?0: menu2.getSort());
                })
                .collect(Collectors.toList());
        return level;
    }

    @Override
    public void removeCategoryByIds(List<Long> asList) {
        //TODO 1、检查待删除的菜单是否被别处引用

        /*
         * 逻辑删除
         * 配置全局的逻辑的删除规则
         *
         */
        //MyBatis Plus 中定义的方法，通用查询操作 deleteBatchIds 通过多个ID进行删除
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogId(Long categoryId) {
        List<Long> list=new ArrayList<>();
        CategoryEntity categoryEntity=getById(categoryId);
        if(categoryEntity.getParentCid()!=0){
            Long parentId=categoryEntity.getParentCid();
            if(getById(parentId).getParentCid() !=0 )
            {
                list.add(getById(parentId).getParentCid());
            }
            list.add(categoryEntity.getParentCid());;
        }
        list.add(categoryId);
        Long[] path = new Long[list.size()];
        int i=0;
        for (Long item :list){
            path[i]=item;
            i++;

        }
        return path;
    }

    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();

        //递归查询是否还有父节点
        List<Long> parentPath = findParentPath(catelogId, paths);

        //进行一个逆序排列
        Collections.reverse(parentPath);

        return (Long[]) parentPath.toArray(new Long[parentPath.size()]);
    }

    private List<Long> findParentPath(Long catelogId, List<Long> paths) {

        //1、收集当前节点id
        paths.add(catelogId);

        //根据当前分类id查询信息
        CategoryEntity byId = this.getById(catelogId);
        //如果当前不是父分类
        if (byId.getParentCid() != 0) {
            findParentPath(byId.getParentCid(), paths);
        }

        return paths;
    }


    //递归查找所有菜单的子菜单
    private List<CategoryEntity> getChildrens(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> children=all.stream().filter(categoryEntity ->{
            return categoryEntity.getParentCid().equals(root.getCatId());
        }).map((categoryEntity)->{
            //找到子菜单
                    categoryEntity.setChildren(getChildrens(categoryEntity,all));
                    return categoryEntity;
                })
                .sorted((menu1,menu2)->{
                    //菜单的排序
                    return (menu1.getSort()==null?0: menu1.getSort())-(menu2.getSort()==null?0: menu2.getSort());
                })
                .collect(Collectors.toList());

return children;
    }

}