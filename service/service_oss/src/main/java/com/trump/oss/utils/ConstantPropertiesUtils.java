package com.trump.oss.utils;

import lombok.Data;
import org.junit.Test;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//读取配置文件内容
@Data
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class ConstantPropertiesUtils implements InitializingBean {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;

    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKET_NAME = bucketName;
    }


}
