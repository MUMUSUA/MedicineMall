package com.example.mall.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.product.entity.BrandEntity;
import com.example.mall.product.vo.BrandVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.product.entity.CategoryBrandRelationEntity;
import com.example.mall.product.service.CategoryBrandRelationService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:15
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取当前品牌关联的所有分类列表列表
     */
    @GetMapping(value = "/catelog/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R catelogList(@RequestParam Map<String, Object> params,@RequestParam("brandId") Long brandId){

        List<CategoryBrandRelationEntity> data = categoryBrandRelationService.
                list(new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));

        return R.ok().put("data", data);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * /product/categorybrandrelation/brands/list
     * 1、Controller：处理请求，接收和效验数据
     * 2、Service接收Controller传来的数据，进行业务处理
     * 3、Controller接收Service处理完的数据，封装页面指定的vo
     */
    @GetMapping(value = "/brands/list")
    public R relationBransList(@RequestParam(value = "catId",required = true) Long catId) {

       //List<BrandEntity> vos = categoryBrandRelationService.getBrandsByCatId(catId);
//
//        List<BrandVo> collect = vos.stream().map(item -> {
//            BrandVo brandVo = new BrandVo();
//            brandVo.setBrandId(item.getBrandId());
//            brandVo.setBrandName(item.getName());
//            return brandVo;
//        }).collect(Collectors.toList());
//        return R.ok().put("data",vos);

     //   List<BrandVo> collect = categoryBrandRelationService.getBrandsByCatId2(catId);

//        List<BrandVo> collect = vos.stream().map(item -> {
//            BrandVo brandVo = new BrandVo();
//            brandVo.setBrandId(item.getBrandId());
//            brandVo.setBrandName(item.getName());
//            return brandVo;
//        }).collect(Collectors.toList());
//        List<BrandVo> collect=null;
//        for(BrandEntity item:vos){
//            BrandVo brandVo = new BrandVo();
//            brandVo.setBrandId(item.getBrandId());
//            brandVo.setBrandName(item.getName());
//            collect.add(brandVo);
//        }
        List<BrandVo> collect =new ArrayList<>();
        BrandVo b =new BrandVo();
        b.setBrandId(5L);
        b.setBrandName("三九药业");
        collect.add(b);
        BrandVo b1 =new BrandVo();
        b1.setBrandId(6L);
        b1.setBrandName("以岭药业");
        collect.add(b1);
        return R.ok().put("data",collect);
    }



}
