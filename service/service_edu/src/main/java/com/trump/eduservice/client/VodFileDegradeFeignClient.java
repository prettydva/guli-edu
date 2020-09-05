package com.trump.eduservice.client;

import com.trump.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class VodFileDegradeFeignClient implements VodClient{
    //出错之后执行
    @Override
    public R removeVideo(String videoId) {

        return R.error().message("调用微服务[删除视频]失败");
    }

    @Override
    public R removeVideoBatch(List<String> videoIdList) {
        return R.error().message("调用微服务[删除多个视频]失败");
    }
}
