package com.example.mall.user.controller;

import java.util.Arrays;
import java.util.Map;

import com.example.common.exception.BizCodeEnum;
import com.example.mall.user.exception.PhoneException;
import com.example.mall.user.exception.UsernameException;
import com.example.mall.user.feign.OrderFeignService;
import com.example.mall.user.vo.MemberRegistVo;
import com.example.mall.user.vo.MemberLoginVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.mall.user.entity.MemberEntity;
import com.example.mall.user.service.MemberService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 会员
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 20:06:01
 */
@RestController
@RequestMapping("user/user")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderFeignService orderFeignService;

    @RequestMapping("/orders")
    public R test(){
        MemberEntity member=new MemberEntity();
        member.setNickname("张三");
        R userOrders = orderFeignService.userOrders();

        return R.ok().put("user",member).put("orders",userOrders.get("orders"));

    }


    @PostMapping(value = "/login")
    public R login(@RequestBody MemberLoginVo vo) {

        MemberEntity memberEntity = memberService.login(vo);

        if (memberEntity != null) {
            return R.ok().setData(memberEntity);
        } else {
            return R.error(BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getCode(),BizCodeEnum.LOGINACCT_PASSWORD_EXCEPTION.getMessage());
        }
    }



    @PostMapping(value = "/register")
    public R register(@RequestBody MemberRegistVo vo) {

        try {
            memberService.register(vo);
        } catch (PhoneException e) {
            return R.error(BizCodeEnum.PHONE_EXIST_EXCEPTION.getCode(),BizCodeEnum.PHONE_EXIST_EXCEPTION.getMessage());
        } catch (UsernameException e) {
            return R.error(BizCodeEnum.USER_EXIST_EXCEPTION.getCode(),BizCodeEnum.USER_EXIST_EXCEPTION.getMessage());
        }

        return R.ok();
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
