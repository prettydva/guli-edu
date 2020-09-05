package com.trump.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.edusta.entity.StatisticsDaily;
import com.trump.edusta.mapper.StatisticsDailyMapper;
import com.trump.edusta.service.StatisticsDailyService;
import com.trump.edusta.client.UcenterClient;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-09-01
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;
    @Override
    public boolean registerCount(String date) {
        //远程调用获取某一天注册人数
        Integer count = ucenterClient.countRegister(date);
        //先删除之前的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",date);
        this.remove(wrapper);
        //添加到数据库
        StatisticsDaily daily = new StatisticsDaily();
        daily.setCourseNum(RandomUtils.nextInt(100,200));//课程数
        daily.setLoginNum(RandomUtils.nextInt(100,200));//登录数
        daily.setVideoViewNum(RandomUtils.nextInt(100,200));//视频播放数
        daily.setRegisterNum(count);//注册人数
        daily.setDateCalculated(date);//统计日期
        return this.save(daily);
    }

    //图表显示，分别返回日期和数量数组
    @Override
    public Map<String, Object> showData(String type, String begin, String end) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> list = this.list(wrapper);
        //返回两部分数据，日期和日期对应数量，返回2个list集合
        List<String> dateList = new ArrayList<>();
        List<Integer> numList = new ArrayList<>();
        //判断要查询的统计数据的类型，进行封装
        switch (type){
            case "login_num":
                list.forEach(li->{
                    dateList.add(li.getDateCalculated());
                    numList.add(li.getLoginNum());
                });
                break;
            case "register_num":
                list.forEach(li->{
                    dateList.add(li.getDateCalculated());
                    numList.add(li.getRegisterNum());
                });
                break;
            case "video_view_num":
                list.forEach(li->{
                    dateList.add(li.getDateCalculated());
                    numList.add(li.getVideoViewNum());
                });
                break;
            case "course_num":
                list.forEach(li->{
                    dateList.add(li.getDateCalculated());
                    numList.add(li.getCourseNum());
                });
                break;
            default:
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("dateList",dateList);
        map.put("numList",numList);
        return map;
    }
}
