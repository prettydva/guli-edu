package com.trump.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.trump.msm.service.MsmService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(Map<String, Object> param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        DefaultProfile profile = DefaultProfile.getProfile("default","LTAI4GKNqHhFuszjpfRVaRFk","Xm9NPkvsLyelyrFHCmBsQMiyyvJTNP");
        IAcsClient client = new DefaultAcsClient(profile);
        //设置相关固定参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        //设置发送相关参数
        request.putQueryParameter("PhoneNumbers",phone);
        request.putQueryParameter("SignName","我的鼓励在线学习网站");//短信签名
        request.putQueryParameter("TemplateCode","SMS_200710240");//短信模板
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));//验证码转换成json传递

        //最终发送
        try {
            CommonResponse response = client.getCommonResponse(request);
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
