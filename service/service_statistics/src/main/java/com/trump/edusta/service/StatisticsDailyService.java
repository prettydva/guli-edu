package com.trump.edusta.service;

import com.trump.edusta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-09-01
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    boolean registerCount(String date);

    Map<String, Object> showData(String type, String begin, String end);
}
