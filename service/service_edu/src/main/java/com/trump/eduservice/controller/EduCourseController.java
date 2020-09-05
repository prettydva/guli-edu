package com.trump.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduCourseDescriptionService;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduTeacherService;
import com.trump.commonutils.PR;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.EduCourseDescription;
import com.trump.eduservice.entity.status.PublishStatus;
import com.trump.eduservice.entity.vo.CourseInfoVo;
import com.trump.eduservice.entity.vo.CoursePublishVo;
import com.trump.eduservice.entity.vo.CourseQuery;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduCourseDescriptionService;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/course")
@Api(tags = {"课程管理"})
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseDescriptionService descriptionService;

    //添加课程信息
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = courseService.addCourseInfo(courseInfoVo);
        return R.ok().data(id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("/{courseId}")
    public R getCourseInfoById(@PathVariable String courseId){
        CourseInfoVo vo = courseService.getCourseInfoById(courseId);
        return R.ok().data(vo);
    }

    //修改课程信息
    @PutMapping("/{courseId}")
    public R updateCourseInfo(@PathVariable String courseId,
                              @RequestBody CourseInfoVo courseInfoVo){
        courseInfoVo.setId(courseId);
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishVo courseInfo = courseService.getPublishCourseInfo(courseId);
        return R.ok().data(courseInfo);
    }

    //修改课程发布状态
    @PutMapping("/publish/{courseId}")
    public R publish(@PathVariable String courseId){
        EduCourse course = new EduCourse();
        course.setId(courseId);
        //设置课程发布状态
        course.setStatus(PublishStatus.NORMAL);
        courseService.updateById(course);
        return R.ok();
    }

    //分页模糊查询课程列表
    @PostMapping("/page/{currentPage}/{pageSize}")
    public R getByPage(@PathVariable long currentPage,
                       @PathVariable long pageSize,
                       @RequestBody(required = false) CourseQuery query){
        //创建page对象
        Page<EduCourse> coursePage = new Page<>(currentPage,pageSize);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件是否为空，如果不为空则拼接条件
        if (query != null ) {
            String title = query.getTitle();
            String status = query.getStatus();
            if (!StringUtils.isEmpty(title)) {
                wrapper.like("title", title);
            }
            if (!StringUtils.isEmpty(status)) {
                wrapper.eq("status", status);
            }
        }
        //按添加时间倒序排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页，底层封装，把分页所有数据封装到pageTeacher对象里
        courseService.page(coursePage,wrapper);
        //把总记录数和分页数据封装到分页结果对象
        List<EduCourse> records = coursePage.getRecords();
        for (int i = 0; i < records.size(); i++) {
            //得到每一个course
            EduCourse course = records.get(i);
            //查询课程对应的讲师和简介
            EduTeacher teacher = teacherService.getById(course.getTeacherId());
            EduCourseDescription description = descriptionService.getById(course.getId());
            //设置课程讲师名称和课程描述
            course.setTeacherName(teacher.getName());
            course.setDescription(description.getDescription());
        }
        PR<EduCourse> pr = new PR<>(coursePage.getTotal(),coursePage.getRecords());
        //把分页结果对象添加到结果对象
        return R.ok().data(pr);
    }

    //删除课程
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
       boolean flag = courseService.deleteCourse(courseId);
       if (flag){
           return R.ok();
       }else {
           return R.error().message("删除失败！");
       }
    }

}

