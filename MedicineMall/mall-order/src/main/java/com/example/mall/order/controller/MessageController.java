package com.example.mall.order.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mall.order.entity.MessageEntity;
import com.example.mall.order.service.MessageService;
import com.example.common.utils.PageUtils;
import com.example.common.utils.R;



/**
 * 
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 19:29:05
 */
@RestController
@RequestMapping("order/message")
public class MessageController {
    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("order:message:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{messageId}")
    @RequiresPermissions("order:message:info")
    public R info(@PathVariable("messageId") String messageId){
		MessageEntity message = messageService.getById(messageId);

        return R.ok().put("message", message);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("order:message:save")
    public R save(@RequestBody MessageEntity message){
		messageService.save(message);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("order:message:update")
    public R update(@RequestBody MessageEntity message){
		messageService.updateById(message);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("order:message:delete")
    public R delete(@RequestBody String[] messageIds){
		messageService.removeByIds(Arrays.asList(messageIds));

        return R.ok();
    }

}
