package com.trump.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduTeacherService;
import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService teacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping("/getTeacherPage/{currentPage}/{pageSize}")
    public R getTeacherFrontPage(@PathVariable long currentPage, @PathVariable long pageSize) {
        Page<EduTeacher> teacherPage = new Page<>(currentPage, pageSize);
        Map<String, Object> map = teacherService.getTacherFrontPage(teacherPage);
        return R.ok().data(map);
    }
    @GetMapping("/getTeacherInfo/{teacherId}")
    public R getTeacherInfo(@PathVariable String teacherId) {
        //查询讲师基本信息
        EduTeacher teacher = teacherService.getById(teacherId);
        //查询讲师所讲的课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id",teacherId);
        List<EduCourse> courseList = courseService.list(wrapper);
        //封装结果
        Map<String,Object> teacherInfo = new HashMap<>();
        teacherInfo.put("teacher",teacher);
        teacherInfo.put("courseList",courseList);
        return R.ok().data(teacherInfo);
    }

    //远程调用
//    @GetMapping("{/getTeacher/teacherId}")
//    public TeacherVoOrder getTeacherById(@PathVariable String teacherId){
//        TeacherVoOrder teacherVo = new TeacherVoOrder();
//        EduTeacher teacher = teacherService.getById(teacherId);
//        BeanUtils.copyProperties(teacher, teacherVo);
//        return teacherVo;
//    }
}
