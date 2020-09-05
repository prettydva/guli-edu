package com.trump.eduservice.mapper;

import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.CoursePublishVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(@Param("courseId") String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
