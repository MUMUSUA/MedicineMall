package com.example.mall.user.dao;

import com.example.mall.user.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author lmt
 * @email komoji587@gmail.com
 * @date 2022-11-02 20:06:01
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
