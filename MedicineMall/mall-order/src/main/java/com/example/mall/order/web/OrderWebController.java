package com.example.mall.order.web;

import com.example.mall.order.service.OrderService;
import com.example.mall.order.vo.OrderConfirmVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @description:
 * @author:wxl
 * @date:2022/11/18
 **/
@RestController
public class OrderWebController {

    @Autowired
    OrderService orderService;

    @GetMapping("/toTrade")
    public String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo = orderService.confirmOrder();

        model.addAttribute("orderConfirmData",confirmVo);


        //展示订单的数据
        return "confirm";
    }
}
