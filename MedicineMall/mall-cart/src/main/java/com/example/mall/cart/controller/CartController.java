package com.example.mall.cart.controller;

import com.example.mall.cart.service.CartService;
import com.example.mall.cart.vo.Cart;
import com.example.mall.cart.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
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

    /**
     * 获取当前用户的购物车商品项
     * @return
     */
    @GetMapping(value = "/currentUserCartItems")
    @ResponseBody
    public List<CartItem> getCurrentCartItems() {

        List<CartItem> cartItemVoList = cartService.getUserCartItems();

        return cartItemVoList;
    }

    @RequestMapping("/cart.html")
    public String CartListPage(Model model) throws ExecutionException, InterruptedException {
        // ThreadLocal快速得到用户信息 userId,userKey
        Cart cart = cartService.getCart();
        model.addAttribute("cart", cart);
        return "cartList2";
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
                            RedirectAttributes  ra ) throws ExecutionException, InterruptedException {

        cartService.addToCart(skuId, num);
        ra.addAttribute("skuId",skuId);
        return "redirect:http://cart.mall.com/addToCartSuccessPage.html";
    }
    /**
     * 添加商品到购物车
     * attributes.addFlashAttribute():将数据放在session中，可以在页面中取出，但是只能取一次
     * attributes.addAttribute():将数据放在url后面
     * @return
     */
    @RequestMapping( "/addToCartSuccessPage.html")
    public String addToCartSuccessPage(@RequestParam("skuId") Long skuId, Model model) {
        //重定向到成功页面，再次查询购物车数据即可
        CartItem item = cartService.getCartItem(skuId);
        model.addAttribute("item", item);
        return "success";
    }
    @GetMapping("/checkItem")
    public String checkItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("check") Integer check) {

        cartService.checkItem(skuId,check);

        return "redirect:http://cart.mall.com/cart.html";
    }
    @GetMapping("/countItem")
    public String countItem(@RequestParam("skuId") Long skuId,
                            @RequestParam("num") Integer num) {

        cartService.changeItemCount(skuId,num);

        return "redirect:http://cart.mall.com/cart.html";
    }
    @GetMapping("deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {

        cartService.deleteItem(skuId);

        return "redirect:http://cart.mall.com/cart.html";
    }
}

