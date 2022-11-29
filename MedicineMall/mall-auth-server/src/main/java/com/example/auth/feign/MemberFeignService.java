package com.example.auth.feign;

import com.example.auth.vo.UserRegistVo;
import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("mall-member")
public interface MemberFeignService {
    @PostMapping(value = "/member/member/register")
    R register(@RequestBody UserRegistVo vo);


}