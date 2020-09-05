package com.trump.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.trump.eduorder.entity.Order;
import com.trump.eduorder.entity.PayLog;
import com.trump.eduorder.mapper.PayLogMapper;
import com.trump.eduorder.service.OrderService;
import com.trump.eduorder.service.PayLogService;
import com.trump.commonutils.ResultCode;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.eduorder.utils.ConstantOrderUtils;
import com.trump.eduorder.utils.HttpClient;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.eduorder.entity.Order;
import com.trump.eduorder.entity.PayLog;
import com.trump.eduorder.mapper.PayLogMapper;
import com.trump.eduorder.service.OrderService;
import com.trump.eduorder.service.PayLogService;
import com.trump.eduorder.utils.ConstantOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {
    @Autowired
    private OrderService orderService;

    //生成二维码
    @Override
    public Map<String, Object> createNative(String orderNo) {
        try {
            Map<String, String> map = new HashMap<>();
            //根据订单号查询订单信息
            Order order = orderService.getByOrderNo(orderNo);
            map.put("appid", ConstantOrderUtils.APP_ID);
            map.put("mch_id", ConstantOrderUtils.MACH_ID);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("body", order.getCourseTitle());
            map.put("out_trade_no", orderNo);
            map.put("total_fee", order.getTotalFee()+"");
            map.put("spbill_create_ip", ConstantOrderUtils.SP_BILL_CREATE_IP);
            map.put("notify_url", ConstantOrderUtils.NOTIFY_URL);
            map.put("trade_type", ConstantOrderUtils.TRADE_TYPE);
            System.out.println("生成二维码传入参数:" + map);

            //发送httpclient请求，传递xml格式参数,微信支付提供的固定地址
            HttpClient client = new HttpClient(ConstantOrderUtils.PAY_URL);
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, ConstantOrderUtils.PARTNER_KEY));
            client.setHttps(true);
            //发送请求
            client.post();
            //得到发送请求返回结果,是xml格式
            String content = client.getContent();
            //把xml转换为map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            System.out.println("生成二维码返回结果:" + resultMap);
            //最终返回数据封装
            Map<String, Object> m = new HashMap<>();
            m.put("out_trade_no", orderNo);
            m.put("course_id", order.getCourseId());
            m.put("total_fee", order.getTotalFee());
            m.put("result_code", resultMap.get("result_code"));//返回二维码操作状态码
            m.put("code_url", resultMap.get("code_url"));//二维码地址
            return m;
        } catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, "生成二维码失败");
        }
    }

    //查询订单支付状态
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //封装参数
            Map<String, String> map = new HashMap<>();
            map.put("appid", ConstantOrderUtils.APP_ID);
            map.put("mch_id", ConstantOrderUtils.MACH_ID);
            map.put("nonce_str", WXPayUtil.generateNonceStr());
            map.put("out_trade_no", orderNo);
            //发送httpclient
            HttpClient client = new HttpClient(ConstantOrderUtils.QUERY_URL);
            //设置xml格式的参数
            client.setXmlParam(WXPayUtil.generateSignedXml(map, ConstantOrderUtils.PARTNER_KEY));
            client.setHttps(true);
            client.post();
            //得到请求内容
            String content = client.getContent();
            //把xml转换为map集合
            return WXPayUtil.xmlToMap(content);
        } catch (Exception e) {
            throw new GuliException(ResultCode.ERROR, "查询支付状态失败..");
        }
    }

    //更新订单状态
    @Override
    public boolean updateOrderStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        Order order = orderService.getByOrderNo(orderNo);
        //更新订单表
        if (order.getStatus() == 1) return true;
        order.setStatus(1);//1代表已支付
        boolean update = orderService.updateById(order);
        if (!update) {
            throw new GuliException(ResultCode.ERROR, "订单状态更新失败..");
        }
        //向支付日志表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);//订单号
        payLog.setPayTime(new Date());//订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额

        payLog.setTradeState(map.get("trade_state"));
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));
        boolean save = this.save(payLog);
        if (!save) {
            throw new GuliException(ResultCode.ERROR, "支付日志更新失败..");
        }
        return true;
    }
}