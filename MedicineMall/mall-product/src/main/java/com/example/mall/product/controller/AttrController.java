package com.example.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

<<<<<<< HEAD
=======
import com.example.mall.product.entity.ProductAttrValueEntity;
import com.example.mall.product.service.ProductAttrValueService;
>>>>>>> fdad2d4878c203cec567e0d6d9a52902cee09a36
import com.example.mall.product.vo.AttrRespVo;
import com.example.mall.product.vo.AttrVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.product.entity.AttrEntity;
import com.example.mall.product.service.AttrService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 商品属性
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 17:22:16
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;
    /**
     *  获取spu规格
     */
    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrlistforspu(@PathVariable("spuId") Long spuId){

        List<ProductAttrValueEntity> entities = productAttrValueService.baseAttrListforspu(spuId);

        return R.ok().put("data",entities);
    }
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


//    @GetMapping("/{attrType}/list/{catelogId}")
    @GetMapping("/{attrType}/list/{catelogId}")

    public R baseAttrList(@RequestParam Map<String, Object> params,
                          @PathVariable("catelogId") Long catelogId,
                          @PathVariable("attrType") String type
                         ){

        PageUtils page = attrService.queryBaseAttrPage(params,catelogId,type);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    @RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
        AttrRespVo attrRespVo=attrService.getAttrInfo(attrId);
		//AttrEntity attr = attrService.getById(attrId);

        return R.ok().put("attr", attrRespVo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrRespVo attr){
		attrService.updateAttrResp(attr);

        return R.ok();
    }


    ///product/attr/update/{spuId} 规格维护中修改规格参数
    @PostMapping("/update/{spuId}")
    //@RequiresPermissions("product:attr:update")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities){

        productAttrValueService.updateSpuAttr(spuId,entities);

        return R.ok();
    }


    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
