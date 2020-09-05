package com.trump.vod.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "aliyun.vod.file")
public class ConstantVodUtils implements InitializingBean {
    private String keyId;
    private String keySecret;

    public static String KEYID;
    public static String KEYSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEYID = keyId;
        KEYSECRET = keySecret;
    }

}
