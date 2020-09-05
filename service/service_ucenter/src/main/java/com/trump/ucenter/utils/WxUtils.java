package com.trump.ucenter.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wx.open.app")
public class WxUtils implements InitializingBean {
    private String id;
    private String secret;
    private String redirect_url;

    public static String OPEN_ID;
    public static String SECRET;
    public static String REDIRECT_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        OPEN_ID = id;
        SECRET = secret;
        REDIRECT_URL = redirect_url;
    }

}
