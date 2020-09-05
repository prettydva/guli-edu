package com.trump.eduservice.client;

import com.trump.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-vod",fallback = VodFileDegradeFeignClient.class)
@Component
public interface VodClient {
    //定义调用的方法路径
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/{videoId}")
    R removeVideo(@PathVariable("videoId") String videoId);

    //删除多个阿里云视频
    @DeleteMapping("/eduvod/video/deleteBatch")
    R removeVideoBatch(@RequestParam("videoIdList") List<String> videoIdList);
}
