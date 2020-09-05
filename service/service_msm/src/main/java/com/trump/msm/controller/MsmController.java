package com.trump.msm.controller;

import com.trump.commonutils.R;
import com.trump.msm.service.MsmService;
import com.trump.commonutils.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable String phone) {
        //1.从redis获取验证码，有就返回
        String code = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(code)){
            return R.ok();
        }
        //2.没有，进行阿里云发送短信
        //生成随机值，传递阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String, Object> param = new HashMap<>();
        param.put("code", code);
        //调用service发送短信
        boolean isSend = msmService.send(param, phone);
        if (isSend) {
            //发送成功，添加到redis里面并设置有效时间，5分钟
            redisTemplate.opsForValue().set("phone",code,5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error();
        }
    }
}
