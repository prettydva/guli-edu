package com.trump.edusta.controller;


import com.trump.commonutils.R;
import com.trump.edusta.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-09-01
 */
@RestController
@RequestMapping("/edusta/sta")
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService staService;

    //统计某一天注册人数，加到数据库中
    @PostMapping("/registerCount")
    public R registerCount(@RequestBody String date){
       boolean save = staService.registerCount(date);
       if (!save)return R.error().message("新增统计数据失败！");
       return R.ok();
    }

    //图表显示，分别返回日期和数量数组
    @GetMapping("/showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,
                           @PathVariable String begin,
                           @PathVariable String end){
        Map<String,Object> map = staService.showData(type,begin,end);
        return R.ok().data(map);
    }

}

