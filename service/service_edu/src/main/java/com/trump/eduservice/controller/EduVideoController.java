package com.trump.eduservice.controller;


import com.trump.eduservice.service.EduVideoService;
import com.trump.commonutils.R;
import com.trump.eduservice.client.VodClient;
import com.trump.eduservice.entity.EduVideo;
import com.trump.servicebase.exceptionhandler.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    @Autowired
    private VodClient vodClient;

    //添加小节
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo video){
        videoService.save(video);
        return R.ok();
    }
    //修改小节
    @PutMapping("/{videoId}")
    public R updateVideo(@PathVariable String videoId,
            @RequestBody EduVideo video){
        video.setId(videoId);
        videoService.updateById(video);
        return R.ok();
    }
    //删除小节
    @DeleteMapping("/{videoId}")
    public R deleteVideo(@PathVariable String videoId){
        //根据小节id获取视频id，调用方法删除视频
        EduVideo video = videoService.getById(videoId);
        String videoSourceId = video.getVideoSourceId();
        //判断小节里是否有视频id
        if (!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程调用实现视频删除
            R r = vodClient.removeVideo(videoSourceId);
            if (r.getCode()==20001){
                throw new GuliException(20001,"删除视频失败...熔断器");
            }
        }
        videoService.removeById(videoId);
        return R.ok();
    }
    //根据id查询小节
    @GetMapping("/{videoId}")
    public R getVideoById(@PathVariable String videoId){
        EduVideo video = videoService.getById(videoId);
        return R.ok().data(video);
    }
}

