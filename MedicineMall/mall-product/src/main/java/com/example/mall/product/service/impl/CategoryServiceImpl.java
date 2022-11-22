package com.example.mall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.mall.product.service.CategoryBrandRelationService;
import com.example.mall.product.vo.Catelog2Vo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.product.dao.CategoryDao;
import com.example.mall.product.entity.CategoryEntity;
import com.example.mall.product.service.CategoryService;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    CategoryBrandRelationService categoryBrandRelationService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

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

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        System.out.println("getLevel1Categorys........");
        long l = System.currentTimeMillis();
        List<CategoryEntity> categoryEntities = this.baseMapper.selectList(
                new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        System.out.println("消耗时间："+ (System.currentTimeMillis() - l));
        return categoryEntities;
    }

    @Override
    public Map<String, List<Catelog2Vo>> getCatalogJson(){
        /**
         * 1,空结果缓存，解决缓存穿透
         * 2,设置过期时间（加随机值），解决缓存雪崩
         * 3,加锁，解决缓存击穿
         */

        //加入缓存逻辑
        String categoryJSON=stringRedisTemplate.opsForValue().get("categoryJSON");
        if(StringUtils.isEmpty(categoryJSON))
        {
            //缓存中没有，查询数据库
            Map<String,List<Catelog2Vo>> catalogJsonFromDb=getCatalogJsonFromDb();
            //查到的缓存放入缓存,将对象转为JSON放在缓存中,用时再逆转为可用的对象，序列化与反序列化
            String s = JSON.toJSONString(catalogJsonFromDb);
            stringRedisTemplate.opsForValue().set("categoryJSON",s,1, TimeUnit.DAYS);
        }
        Map<String,List<Catelog2Vo>> catelog=JSON.parseObject(categoryJSON,new TypeReference<Map<String,List<Catelog2Vo>>>(){});
        return  catelog;
    }


    //@Cacheable(value = "category",key = "#root.methodName")
    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDb() {
        System.out.println("查询了数据库");

        //将数据库的多次查询变为一次
        List<CategoryEntity> selectList = this.baseMapper.selectList(null);
        //1、查出所有分类
        //1、1）查出所有一级分类
        List<CategoryEntity> level1Categorys = getParent_cid(selectList, 0L);

        //封装数据
        Map<String, List<Catelog2Vo>> parentCid = level1Categorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            //1、每一个的一级分类,查到这个一级分类的二级分类
            List<CategoryEntity> categoryEntities = getParent_cid(selectList, v.getCatId());

            //2、封装上面的结果
            List<Catelog2Vo> catelog2Vos = null;
            if (categoryEntities != null) {
                catelog2Vos = categoryEntities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null, l2.getCatId().toString(), l2.getName().toString());

                    //1、找当前二级分类的三级分类封装成vo
                    List<CategoryEntity> level3Catelog = getParent_cid(selectList, l2.getCatId());

                    if (level3Catelog != null) {
                        List<Catelog2Vo.Category3Vo> category3Vos = level3Catelog.stream().map(l3 -> {
                            //2、封装成指定格式
                            Catelog2Vo.Category3Vo category3Vo = new Catelog2Vo.Category3Vo(l2.getCatId().toString(), l3.getCatId().toString(), l3.getName());

                            return category3Vo;
                        }).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(category3Vos);
                    }

                    return catelog2Vo;
                }).collect(Collectors.toList());
            }

            return catelog2Vos;
        }));

        return parentCid;
    }
    private List<CategoryEntity> getParent_cid(List<CategoryEntity> selectList,Long parentCid) {
        List<CategoryEntity> categoryEntities = selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
        return categoryEntities;
        // return this.baseMapper.selectList(
        //         new QueryWrapper<CategoryEntity>().eq("parent_cid", parentCid));
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
 //           return categoryEntity.getParentCid().equals(root.getCatId());
          return categoryEntity.getParentCid().longValue() == root.getCatId().longValue();
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