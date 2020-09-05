package com.trump.eduservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order", fallback = OrderDegradeFeignClient.class)
@Component
public interface OrderClient {
    @GetMapping("/eduorder/order/isBuyCourse/{memberId}/{courseId}")
    boolean isBuyCourse(@PathVariable("memberId") String memberId,
                        @PathVariable("courseId") String courseId);
}
