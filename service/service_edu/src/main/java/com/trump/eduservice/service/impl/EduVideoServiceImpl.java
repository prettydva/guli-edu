package com.trump.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.eduservice.service.EduVideoService;
import com.trump.eduservice.client.VodClient;
import com.trump.eduservice.entity.EduVideo;
import com.trump.eduservice.mapper.EduVideoMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    //根据课程id删除小节
    @Autowired
    private VodClient vodClient;

    @Override
    public boolean removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        wrapperVideo.select("video_source_id");
        //查询所有视频id
        List<EduVideo> list = this.list(wrapperVideo);
        List<String> videoIds = new ArrayList<>();
        //把视频id放到String集合中
        //判断是否为空
//        System.out.println("list:"+list);
        list.stream().filter(video -> video!=null && !StringUtils.isEmpty(video.getVideoSourceId()))
                .forEach(video -> videoIds.add(video.getVideoSourceId()));
        //根据多个视频id删除多个视频
//        System.out.println("videoIds:"+videoIds);
        if(videoIds.size()>0){
            vodClient.removeVideoBatch(videoIds);
        }
        //删除小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        return this.remove(wrapper);
    }
}
