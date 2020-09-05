package com.trump.edusta.schedule;

import com.trump.edusta.service.StatisticsDailyService;
import com.trump.edusta.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledTask {
    @Autowired
    private StatisticsDailyService staService;
    //0/5 * * * * ? 每隔5s执行一次这个方法
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void task1() {
//        System.out.println("task1执行了");
//    }
    //每天凌晨1点，执行方法，把数据查询进行添加
    @Scheduled(cron = "0 0 1 * * ?")
    public void updateSta(){
        staService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(),-1)));
    }
}
