package com.trump.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.trump.vod.service.VodService;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.vod.utils.ConstantVodUtils;
import com.trump.vod.utils.InitVodClient;
import com.trump.vod.utils.ConstantVodUtils;
import com.trump.vod.utils.InitVodClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service("vodService")
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        try {
            //osskey和密钥
            String accessKeyId = ConstantVodUtils.KEYID;
            String accessKeySecret = ConstantVodUtils.KEYSECRET;
            //文件原始名称
            String fileName = file.getOriginalFilename();
            //上传之后显示名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);
            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //删除多个视频
    @Override
    public void removeVideoBatch(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initvodClient(ConstantVodUtils.KEYID, ConstantVodUtils.KEYSECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置视频id
            //videoIdList值转换为1,2,3...
            String videoIds = String.join(",", videoIdList);
            request.setVideoIds(videoIds);
            //调用初始化对象删除视频
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }
}
