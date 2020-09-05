package com.trump.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.commonutils.JwtUtils;
import com.trump.commonutils.R;
import com.trump.commonutils.ordervo.CourseVoOrder;
import com.trump.eduservice.client.OrderClient;
import com.trump.eduservice.entity.frontVo.CourseFrontVo;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.ChapterVo;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.frontVo.CourseFrontVo;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.ChapterVo;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduCourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduCourseService courseService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private OrderClient orderClient;

    //分页模糊查询课程
    @PostMapping("/getCoursePage/{page}/{size}")
    public R getCourseFrontPage(@PathVariable long page,
                                @PathVariable long size,
                                @RequestBody(required = false) CourseFrontVo vo) {
//        System.out.println("ctl:"+vo);
        Page<EduCourse> coursePage = new Page<>();
        Map<String, Object> map = courseService.getCoursePage(coursePage, vo);
        return R.ok().data(map);
    }

    //课程详情
    @GetMapping("/{courseId}")
    public R getOne(@PathVariable String courseId, HttpServletRequest request) {
        //根据id查询课程信息
        CourseWebVo course = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        //根据request获取memberId
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        System.out.println("memberId:"+memberId);
        //远程调用查询课程是否购买
        boolean isBuyCourse = orderClient.isBuyCourse(memberId, courseId);
        System.out.println("isBuyCourse:"+isBuyCourse);
        System.out.println("orderClient:"+orderClient);
        System.out.println("courseId:"+courseId);
        //封装返回数据
        Map<String,Object> map = new HashMap<>();
        map.put("course",course);
        map.put("chapterVideoList",chapterVideoList);
        map.put("isBuyCourse",isBuyCourse);
        System.out.println("map:"+map);
        return R.ok().data(map);
    }
    //提供远程调用的方法
    @GetMapping("/getCourse/{courseId}")
    public CourseVoOrder getById(@PathVariable String courseId) {
        //根据id查询课程信息
        CourseWebVo course = courseService.getBaseCourseInfo(courseId);
        CourseVoOrder courseVo = new CourseVoOrder();
        BeanUtils.copyProperties(course,courseVo);
        return courseVo;
    }
}
