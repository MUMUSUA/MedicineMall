package com.example.auth.feign;

import com.example.auth.config.FeignConfig;
import com.example.auth.vo.UserLoginVo;
import com.example.auth.vo.UserRegistVo;
import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "mall-user",configuration = FeignConfig.class)
public interface MemberFeignService {
    @PostMapping(value = "/user/user/register")
    R register(@RequestBody UserRegistVo vo);

    @PostMapping(value = "/user/user/login")
    R login(@RequestBody UserLoginVo vo);

}
