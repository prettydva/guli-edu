package com.trump.eduservice.client;

import org.springframework.stereotype.Component;


@Component
public class OrderDegradeFeignClient implements OrderClient{
    //出错之后执行
    @Override
    public boolean isBuyCourse(String memberId, String courseId) {
        return false;
    }
}
