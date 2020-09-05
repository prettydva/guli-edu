package com.trump.eduorder.service;

import com.trump.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduorder.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
public interface PayLogService extends IService<PayLog> {

    Map<String,Object> createNative(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    boolean updateOrderStatus(Map<String, String> map);
}
