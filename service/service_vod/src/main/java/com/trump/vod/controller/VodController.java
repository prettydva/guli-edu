package com.trump.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.trump.commonutils.R;
import com.trump.commonutils.ResultCode;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.vod.service.VodService;
import com.trump.vod.utils.ConstantVodUtils;
import com.trump.vod.utils.InitVodClient;
import com.trump.vod.service.VodService;
import com.trump.vod.utils.ConstantVodUtils;
import com.trump.vod.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @PostMapping("/uploadVideo")
    public R uploadVideo(MultipartFile file) {
        String videoId = vodService.uploadVideo(file);
        return R.ok().data(videoId);
    }

    //根据视频Id删除阿里云视频
    @DeleteMapping("{videoId}")
    public R removeVideo(@PathVariable String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initvodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置视频id
            request.setVideoIds(videoId);
            //调用初始化对象删除视频
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
        return R.ok();
    }

    //删除多个阿里云视频
    @DeleteMapping("/deleteBatch")
    public R removeVideoBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeVideoBatch(videoIdList);
        return R.ok();
    }

    //根据视频id获取视频凭证
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initvodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            return R.ok().data(response.getPlayAuth());
        } catch (ClientException e) {
            throw new GuliException(ResultCode.ERROR,"获取凭证失败..");
        }
    }
}
