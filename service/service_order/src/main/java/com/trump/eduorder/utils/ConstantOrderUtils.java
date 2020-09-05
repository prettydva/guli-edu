package com.trump.eduorder.utils;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wx.open.app")
public class ConstantOrderUtils implements InitializingBean {
    private String id;
    private String partner;
    private String partnerkey;
    private String notifyUrl;
    private String spBillCreateIp;
    private String tradeType;
    private String payUrl;
    private String queryUrl;

    public static String APP_ID;
    public static String MACH_ID;
    public static String NOTIFY_URL;
    public static String SP_BILL_CREATE_IP;
    public static String TRADE_TYPE;
    public static String PAY_URL;
    public static String PARTNER_KEY;
    public static String QUERY_URL;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = id;
        MACH_ID = partner;
        NOTIFY_URL = notifyUrl;
        SP_BILL_CREATE_IP = spBillCreateIp;
        TRADE_TYPE = tradeType;
        PAY_URL = payUrl;
        PARTNER_KEY = partnerkey;
        QUERY_URL=queryUrl;
    }

}
