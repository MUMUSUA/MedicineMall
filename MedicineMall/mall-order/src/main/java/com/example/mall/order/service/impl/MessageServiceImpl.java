package com.example.mall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;
import com.example.mall.order.dao.MessageDao;
import com.example.mall.order.entity.MessageEntity;
import com.example.mall.order.service.MessageService;


@Service("messageService")
public class MessageServiceImpl extends ServiceImpl<MessageDao, MessageEntity> implements MessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MessageEntity> page = this.page(
                new Query<MessageEntity>().getPage(params),
                new QueryWrapper<MessageEntity>()
        );

        return new PageUtils(page);
    }

}