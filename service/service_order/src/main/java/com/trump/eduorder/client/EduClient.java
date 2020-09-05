package com.trump.eduorder.client;

import com.trump.commonutils.ordervo.CourseVoOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-edu")
@Component
public interface EduClient {
    //查询课程信息
    @GetMapping("/eduservice/coursefront/getCourse/{courseId}")
    CourseVoOrder getCourse(@PathVariable("courseId") String courseId);
}
