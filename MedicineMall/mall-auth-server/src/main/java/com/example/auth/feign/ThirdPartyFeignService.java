package com.example.auth.feign;

import com.example.auth.config.FeignConfig;
import com.example.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mall-thirdParty",configuration = FeignConfig.class )

public interface ThirdPartyFeignService {

    @GetMapping("/data/send_sms/sendcode")
     R sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
