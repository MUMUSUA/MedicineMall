package com.example.mall.user.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.mall.user.dao.MemberReceiveAddressDao;
import com.example.mall.user.entity.MemberReceiveAddressEntity;
import com.example.mall.user.service.MemberReceiveAddressService;


@Service("memberReceiveAddressService")
public class MemberReceiveAddressServiceImpl extends ServiceImpl<MemberReceiveAddressDao, MemberReceiveAddressEntity> implements MemberReceiveAddressService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberReceiveAddressEntity> page = this.page(
                new Query<MemberReceiveAddressEntity>().getPage(params),
                new QueryWrapper<MemberReceiveAddressEntity>()
        );
        return new PageUtils(page);
    }

    /**
     * 收货地址列表的实现
     * @param memberId
     * @return
     */
    @Override
    public List<MemberReceiveAddressEntity> getAddress(Long memberId){
        //根据会员id查出所有收货地址列表，然后将列表返回
        return this.list(new QueryWrapper<MemberReceiveAddressEntity>().eq("member_id",memberId));
    }

}