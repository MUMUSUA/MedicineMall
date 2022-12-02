package com.example.mall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.example.common.to.mq.SeckillOrderTo;
import com.example.common.utils.R;
import com.example.common.vo.MemberResponseVo;
import com.example.mall.order.config.MyThreadConfig;
import com.example.mall.order.entity.OrderItemEntity;
import com.example.mall.order.entity.PaymentInfoEntity;
import com.example.mall.order.feign.CartFeignService;
import com.example.mall.order.feign.MemberFeignService;
import com.example.mall.order.feign.WmsFeignService;
import com.example.mall.order.interceptor.LoginUserInterceptor;
import com.example.mall.order.service.OrderItemService;
import com.example.mall.order.vo.*;
import com.example.mall.order.interceptor.LoginUserInterceptor;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;
import com.example.mall.order.dao.OrderDao;
import com.example.mall.order.entity.OrderEntity;
import com.example.mall.order.service.OrderService;


import com.sun.xml.internal.ws.util.CompletedFuture;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.math.BigDecimal;


/**
 * @author wxl
 */
@Service("orderService")
@Primary
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    /**
     * 用户的远程服务
     */
    @Autowired
    MemberFeignService memberFeignService;

    /**
     * 购物车的远程服务
     */
    @Autowired
    CartFeignService cartFeignService;


    /**
     * 库存远程服务
     */
    @Autowired
    WmsFeignService wmsFeignService;

    /**
     * 线程池
     */
    @Autowired
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public OrderConfirmVo confirmOrder() throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = new OrderConfirmVo();
        //通过LoginUserInterceptor获取当前登陆用户的信息
        MemberResponseVo memberRespVo = LoginUserInterceptor.loginUser.get();
        System.out.println("主线程..." + Thread.currentThread().getId());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        //        CompletableFuture.runAsync(()->{},executor);   //将其提交给线程池
        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
            //1、远程调用查询所有收货地址列表
            System.out.println("member线程..." + Thread.currentThread().getId());
            RequestContextHolder.getRequestAttributes();
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        }, threadPoolExecutor);

        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            //2、远程查询所有购物车中钻中的购物项
            System.out.println("cart线程..." + Thread.currentThread().getId());
            RequestContextHolder.getRequestAttributes();
            List<OrderItemVo> items = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(items);
            //feign在远程调用之前要构造请求，调用拦截器
            //RequestInterceptor interceptor : requestInterceptor
        }, threadPoolExecutor).thenRunAsync(() -> {
            List<OrderItemVo> items = confirmVo.getItems();
            //获取全部商品的id
            List<Long> skuIds = items.stream()
                    .map((itemVo -> itemVo.getSkuId()))
                    .collect(Collectors.toList());
            //远程查询商品库存信息
            R skuHasStock = wmsFeignService.getSkuHasStock(skuIds);
            List<SkuStockVo> skuStockVos = skuHasStock.getData("data", new TypeReference<List<SkuStockVo>>() {
            });

            if (skuStockVos != null && skuStockVos.size() > 0) {
                //将skuStockVos集合转换为map
                Map<Long, Boolean> skuHasStockMap = skuStockVos.stream().collect(Collectors.toMap(SkuStockVo::getSkuId, SkuStockVo::getHasStock));
                confirmVo.setStocks(skuHasStockMap);
            }
        }, threadPoolExecutor);


        //3、查询用户积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);

        //4、其他数据自动计算

        //TODO 5、防重复令牌


        CompletableFuture.allOf(getAddressFuture, cartFuture).get();

        return confirmVo;
    }
}

