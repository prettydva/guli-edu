package com.trump.vod.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

public class InitVodClient {
    public static DefaultAcsClient initvodClient(String keyId,String keySecret){
        String regionId = "cn-shanghai";//点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId,keyId,keySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
