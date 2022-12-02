package com.example.mall.order.feign;

import com.example.mall.order.vo.MemberAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author wxl
 */
@FeignClient("mall-user")
public interface MemberFeignService {

    @GetMapping("/user/memberreceiveaddress/{memberId}/addresses")
    public List<MemberAddressVo> getAddress(@PathVariable("memberId") Long memberId);



}
