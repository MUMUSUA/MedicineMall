package com.example.mall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mall.product.entity.BrandEntity;
import com.example.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MallProductApplicationTests {
    @Autowired
    BrandService brandService;
    @Test
    void contextLoads() {
        BrandEntity brandEntity=new BrandEntity();
//        brandEntity.setName("三一药业");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");
//        brandEntity.setBrandId(12L);
//        brandEntity.setDescript("999感冒灵");
//        brandService.updateById(brandEntity);
//        System.out.println("修改成功");
        List<BrandEntity> list= brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id",12L));
        list.forEach((item)->{
            System.out.println(item);
        });
    }

}
