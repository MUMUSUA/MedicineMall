package com.example.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.utils.PageUtils;
import com.example.mall.order.entity.MessageEntity;

import java.util.Map;

/**
 * 
 *
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 19:29:05
 */
public interface MessageService extends IService<MessageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

