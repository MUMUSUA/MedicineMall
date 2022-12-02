package com.example.mall.order.service.impl;

import com.alibaba.fastjson.TypeReference;
import com.example.common.utils.R;
import com.example.common.vo.MemberResponseVo;
import java.util.concurrent.ThreadPoolExecutor;
import com.example.mall.order.entity.OrderItemEntity;
import com.example.mall.order.feign.CartFeignService;
import com.example.mall.order.feign.MemberFeignService;
import com.example.mall.order.feign.WmsFeignService;
import com.example.mall.order.interceptor.LoginUserInterceptor;
import com.example.mall.order.service.OrderItemService;
import com.example.mall.order.vo.*;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;
import com.example.mall.order.dao.OrderDao;
import com.example.mall.order.entity.OrderEntity;
import com.example.mall.order.service.OrderService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;



/**
 * @author wxl
 */
@Slf4j
@Service("orderService")
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
   ThreadPoolExecutor executor;

    @Autowired
    private OrderItemService orderItemService;
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
        //开启第一个异步任务
        //        CompletableFuture.runAsync(()->{},executor);   //将其提交给线程池
        CompletableFuture<Void> getAddressFuture = CompletableFuture.runAsync(() -> {
            //1、远程调用查询所有收货地址列表
            System.out.println("member线程..." + Thread.currentThread().getId());
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<MemberAddressVo> address = memberFeignService.getAddress(memberRespVo.getId());
            confirmVo.setAddress(address);
        }, executor);

        CompletableFuture<Void> cartFuture = CompletableFuture.runAsync(() -> {
            //2、远程查询所有购物车中钻中的购物项
            System.out.println("cart线程..." + Thread.currentThread().getId());
            RequestContextHolder.setRequestAttributes(requestAttributes);
            List<OrderItemVo> items = cartFeignService.getCurrentUserCartItems();
            confirmVo.setItems(items);
            //feign在远程调用之前要构造请求，调用拦截器
            //RequestInterceptor interceptor : requestInterceptor
        }, executor).thenRunAsync(() -> {
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
        }, executor);


        //3、查询用户积分
        Integer integration = memberRespVo.getIntegration();
        confirmVo.setIntegration(integration);

        //4、其他数据自动计算

        //TODO 5、防重复令牌


        CompletableFuture.allOf(getAddressFuture, cartFuture).get();

        return confirmVo;
    }

    /**
     * 查询当前用户所有订单数据
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPageWithItem(Map<String, Object> params) {

        MemberResponseVo memberResponseVo = LoginUserInterceptor.loginUser.get();

        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>()
                        .eq("member_id",memberResponseVo.getId()).orderByDesc("create_time")
        );

        //遍历所有订单集合
        List<OrderEntity> orderEntityList = page.getRecords().stream().map(order -> {
            //根据订单号查询订单项里的数据
            List<OrderItemEntity> orderItemEntities = orderItemService.list(new QueryWrapper<OrderItemEntity>()
                    .eq("order_sn", order.getOrderSn()));
            order.setOrderItemEntityList(orderItemEntities);
            return order;
        }).collect(Collectors.toList());

        page.setRecords(orderEntityList);

        return new PageUtils(page);
    }
}
