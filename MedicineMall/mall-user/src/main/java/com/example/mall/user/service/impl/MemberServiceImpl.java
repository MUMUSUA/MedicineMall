package com.example.mall.user.service.impl;

import com.example.mall.user.dao.MemberLevelDao;
import com.example.mall.user.entity.MemberLevelEntity;
import com.example.mall.user.exception.PhoneException;
import com.example.mall.user.exception.UsernameException;
import com.example.mall.user.vo.MemberRegistVo;
import com.example.mall.user.vo.MemberLoginVo;
import com.example.mall.user.vo.SocialUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;
import com.example.mall.user.dao.MemberDao;
import com.example.mall.user.entity.MemberEntity;
import com.example.mall.user.service.MemberService;
import javax.annotation.Resource;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Resource
    private MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(MemberRegistVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity memberEntity = new MemberEntity();

        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        memberEntity.setLevelId(levelEntity.getId());

        //设置其它的默认信息
        //检查用户名和手机号是否唯一。感知异常，异常机制
        checkPhoneUnique(vo.getPhone());
        checkUserNameUnique(vo.getUserName());

//        memberEntity.setNickname(vo.getUserName());
//        memberEntity.setUsername(vo.getUserName());

        memberEntity.setMobile(vo.getPhone());
        memberEntity.setUsername(vo.getUserName());

        //密码进行MD5加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode(vo.getPassword());
        memberEntity.setPassword(encode);
//        memberEntity.setMobile(vo.getPhone());
//        memberEntity.setGender(0);
//        memberEntity.setCreateTime(new Date());

        //保存数据
        memberDao.insert(memberEntity);
    }

    @Override
    public void checkPhoneUnique(String phone) throws PhoneException {
        Integer phoneCount = Math.toIntExact(this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone)));

        if (phoneCount > 0) {
            throw new PhoneException();
        }
    }

    @Override
    public void checkUserNameUnique(String userName) throws UsernameException {
        Integer usernameCount = Math.toIntExact(this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName)));
        if (usernameCount > 0) {
            throw new UsernameException();
        }
    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();

        //1、去数据库查询 SELECT * FROM ums_member WHERE username = ? OR mobile = ?
        MemberEntity memberEntity = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", loginacct).or().eq("mobile", loginacct));

        if (memberEntity == null) {
            //登录失败
            return null;
        }
        else {
            //1、获取到数据库里的password
            String passwordDb = memberEntity.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            //2、进行密码匹配
            boolean matches = passwordEncoder.matches(password, passwordDb);
            if (matches) {
                //登录成功
                return memberEntity;
            }else{
                return null;
            }
        }

    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws Exception {
        return null;
    }

    @Override
    public MemberEntity login(String accessTokenInfo) {
        return null;
    }

}
