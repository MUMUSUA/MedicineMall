package com.example.mall.cart.controller;

import com.alibaba.nacos.shaded.org.checkerframework.common.reflection.qual.GetMethod;

import com.example.common.constant.AuthServerConstant;
import com.example.mall.cart.To.UserInfoTo;
import com.example.mall.cart.interceptor.CartInterceptor;
import com.example.mall.cart.service.CartService;
import com.example.mall.cart.vo.Cart;
import com.example.mall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.util.AuthResources_ja;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.concurrent.ExecutionException;

/**
 * @Author: marui
 * @Date: 2022/11/16
 * @Time: 15:29
 * @Description:
 */
@Controller
public class CartController {
    @Autowired
     private  CartService cartService;

    @RequestMapping("/cart.html")
    public String CartListPage(Model model) throws ExecutionException, InterruptedException {
        // ThreadLocal快速得到用户信息 userId,userKey
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList";
    }

    /**
     * 添加商品到购物车
     *
     * @return
     */
//    RedirectAttributes ra
    @RequestMapping ("/addToCart")
    public String addToCart(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num,
                           Model model) throws ExecutionException, InterruptedException {
        CartItem cartItem = cartService.addToCart(skuId, num);

//         cartService.addToCart(skuId, num);
//        ra.addAttribute("skuId",skuId);
//        return "redirect:http://cart.mall.com/addToCartSuccessPage.html";
//    }
//    /**
//     * 添加商品到购物车
//     * attributes.addFlashAttribute():将数据放在session中，可以在页面中取出，但是只能取一次
//     * attributes.addAttribute():将数据放在url后面
//     * @return
//     */
//    @RequestMapping( "/addToCartSuccessPage.html")
//    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
//        //重定向到成功页面，再次查询购物车数据即可
//        CartItem item = cartService.getCartItem(skuId);
        model.addAttribute("item", cartItem);
        return "success";
    }
}

