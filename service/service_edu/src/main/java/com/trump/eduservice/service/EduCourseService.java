package com.trump.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduservice.entity.frontVo.CourseFrontVo;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.CourseInfoVo;
import com.trump.eduservice.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
public interface EduCourseService extends IService<EduCourse> {

    String addCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getPublishCourseInfo(String courseId);

    boolean deleteCourse(String courseId);

    Map<String, Object> getCoursePage(Page<EduCourse> coursePage, CourseFrontVo vo);

    CourseWebVo getBaseCourseInfo(String courseId);
}
