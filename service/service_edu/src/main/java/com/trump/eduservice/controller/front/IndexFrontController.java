package com.trump.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
@RequestMapping("/eduservice/front")
public class IndexFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduTeacherService teacherService;
    //查询前8条热门课程，前4位名师
    @GetMapping("/index")
    public R index(){
        //查询前8条热门课程
        QueryWrapper<EduCourse> courseWrapper = new QueryWrapper<>();
        courseWrapper.orderByDesc("id");
        courseWrapper.last("limit 8");
        List<EduCourse> courseList = courseService.list(courseWrapper);

        //查询前4位名师
        QueryWrapper<EduTeacher> teacherWrapper = new QueryWrapper<>();
        teacherWrapper.orderByDesc("id");
        teacherWrapper.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(teacherWrapper);

        //封装数据
        Map<String,Object> map = new HashMap<>();
        map.put("courseList",courseList);
        map.put("teacherList",teacherList);
        return R.ok().data(map);

    }
}
