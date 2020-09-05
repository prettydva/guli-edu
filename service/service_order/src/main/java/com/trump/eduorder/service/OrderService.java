package com.trump.eduorder.service;

import com.trump.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduorder.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String memberId);

    Order getByOrderNo(String orderNo);
}
