package com.trump.eduorder.controller;


import com.trump.commonutils.R;
import com.trump.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-29
 */
@RestController
@RequestMapping("/eduorder/paylog")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;

    //查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if (map==null){
            return R.error().message("支付出错！");
        }
        System.out.println("查询订单状态:"+map);
        //如果map不为空，获取订单状态
        if ("SUCCESS".equals(map.get("trade_state"))){
            //添加记录到支付表，更新订单表状态
            boolean update = payLogService.updateOrderStatus(map);
            if (update){
                return R.ok().message("支付成功,订单信息已更新!");
            }else {
                return R.error().message("支付成功,订单信息更新失败..");
            }
        }
        return R.ok().code(25000).message("支付中..");
    }

    //生成二维码
    @GetMapping("/createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回信息，包含二维码和其他需要的信息
        Map<String,Object> map = payLogService.createNative(orderNo);
        System.out.println("二维码信息:"+map);
        return R.ok().data(map);
    }
}

