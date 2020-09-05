package com.trump.eduorder.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.eduorder.entity.Order;
import com.trump.eduorder.mapper.OrderMapper;
import com.trump.eduorder.service.OrderService;
import com.trump.commonutils.ResultCode;
import com.trump.commonutils.ordervo.CourseVoOrder;
import com.trump.commonutils.ordervo.MemberVoOrder;
import com.trump.eduorder.client.EduClient;
import com.trump.eduorder.client.UcenterClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.eduorder.utils.OrderNoUtil;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.eduorder.client.EduClient;
import com.trump.eduorder.client.UcenterClient;
import com.trump.eduorder.entity.Order;
import com.trump.eduorder.mapper.OrderMapper;
import com.trump.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    @Autowired
    private EduClient eduClient;
    @Autowired
    private UcenterClient ucenterClient;

    //生成订单
    @Override
    public String createOrder(String courseId, String memberId) {
        //远程调用获取课程和用户信息
        CourseVoOrder course = eduClient.getCourse(courseId);
        MemberVoOrder member = ucenterClient.getMember(memberId);
        //创建order对象，设置值
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        //设置课程信息
        order.setCourseId(courseId);
        order.setCourseTitle(course.getTitle());
        order.setCourseCover(course.getCover());
        //设置用户信息
        order.setMemberId(memberId);
        order.setNickname(member.getNickname());
        order.setMobile(member.getMobile());
        //设置讲师信息
        order.setTeacherName(course.getTeacherName());
        order.setPayType(1);//支付类型，微信
        boolean save = this.save(order);
        if (save) {
            return order.getOrderNo();
        } else {
            throw new GuliException(ResultCode.ERROR, "生成订单失败..");
        }
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        return this.getOne(wrapper);
    }
}
