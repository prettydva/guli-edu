package com.trump.vod;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;

import java.util.List;

public class TestVod {
    public static void main(String[] args) throws ClientException {
        //1.根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GKNqHhFuszjpfRVaRFk","Xm9NPkvsLyelyrFHCmBsQMiyyvJTNP");
        //创建获取视频地址request和response
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request对象里面设置视频id
        request.setVideoId("6dc029c994304637b298e9c012150ad2");
        //调用初始化对象里面的方法，传递request,获取数据
        response = client.getAcsResponse(request);
        System.out.println("auth:"+response.getPlayAuth());
    }
    public static void getPlayUrl(String[] args) throws ClientException {
        //1.根据视频id获取视频播放地址
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI4GKNqHhFuszjpfRVaRFk","Xm9NPkvsLyelyrFHCmBsQMiyyvJTNP");
        //创建获取视频地址request和response
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        //向request对象里面设置视频id
        request.setVideoId("6dc029c994304637b298e9c012150ad2");
        //调用初始化对象里面的方法，传递request,获取数据
        response = client.getAcsResponse(request);
        //播放地址
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("playUrl:" + playInfo.getPlayURL() + "\n");
        }
        //base信息
        System.out.println("videoTitle:"+response.getVideoBase().getTitle()+"\n");
    }
}
