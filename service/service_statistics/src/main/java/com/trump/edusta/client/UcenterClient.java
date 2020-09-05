package com.trump.edusta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-ucenter")
public interface UcenterClient {
    @GetMapping("/ucenter/member/countRegister/{date}")
    Integer countRegister(@PathVariable String date);
}
