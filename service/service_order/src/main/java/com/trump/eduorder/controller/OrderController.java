package com.trump.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.commonutils.JwtUtils;
import com.trump.commonutils.R;
import com.trump.eduorder.entity.Order;
import com.trump.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //生成订单
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request) {
        //创建订单，返回订单号
        String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data(orderNo);

    }

    //根据订单号查询订单信息
    @GetMapping("/getByOrderNo/{orderNo}")
    public R getByOrderNo(@PathVariable String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
        return R.ok().data(order);
    }

    //根据课程Id和用户Id查询课程是否购买
    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    public boolean isBuyCourse(@PathVariable String memberId,
                               @PathVariable String courseId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", memberId);
        wrapper.eq("course_id", courseId);
        wrapper.eq("status", 1);//支付状态 1已支付
        int count = orderService.count(wrapper);
        return count>0;
    }

}

