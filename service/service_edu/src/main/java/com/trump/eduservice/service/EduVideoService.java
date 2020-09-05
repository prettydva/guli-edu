package com.trump.eduservice.service;

import com.trump.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
public interface EduVideoService extends IService<EduVideo> {

    boolean removeVideoByCourseId(String courseId);
}
