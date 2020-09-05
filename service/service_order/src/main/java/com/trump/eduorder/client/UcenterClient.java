package com.trump.eduorder.client;

import com.trump.commonutils.ordervo.MemberVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-ucenter")
@Component
public interface UcenterClient {

    //查询用户信息
    @GetMapping("/ucenter/member/getMember/{memberId}")
    MemberVoOrder getMember(@PathVariable("memberId") String memberId);
}
