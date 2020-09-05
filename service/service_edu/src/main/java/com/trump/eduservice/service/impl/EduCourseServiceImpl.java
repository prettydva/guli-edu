package com.trump.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduCourse;
import com.trump.eduservice.entity.EduCourseDescription;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduCourseDescriptionService;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduVideoService;
import com.trump.commonutils.PR;
import com.trump.eduservice.entity.frontVo.CourseFrontVo;
import com.trump.eduservice.entity.frontVo.CourseWebVo;
import com.trump.eduservice.entity.vo.CourseInfoVo;
import com.trump.eduservice.entity.vo.CoursePublishVo;
import com.trump.eduservice.mapper.EduCourseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduCourseDescriptionService;
import com.trump.eduservice.service.EduCourseService;
import com.trump.eduservice.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

    //添加课程信息
    @Override
    public String addCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //CourseInfoVo对象转换EduCourse对象
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int insert = baseMapper.insert(course);
        if (insert <= 0) {
            throw new GuliException(20001, "添加课程信息失败");
        }
        //获取添加之后的课程id
        String cid = course.getId();
        //2.向课程简介表添加课程基本信息
        EduCourseDescription description = new EduCourseDescription();
        description.setId(cid);
        description.setDescription(courseInfoVo.getDescription());
        boolean save = descriptionService.save(description);
        if (!save) {
            throw new GuliException(20001, "添加课程简介失败");
        }
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfoById(String courseId) {
        //1.查询课程表
        EduCourse course = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);

        //2.查询描述表
        EduCourseDescription description = descriptionService.getById(courseId);
        courseInfoVo.setDescription(description.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程表
        EduCourse course = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if (update == 0) {
            throw new GuliException(20001, "修改课程失败");
        }
        //2.修改课程描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        boolean b2 = descriptionService.updateById(description);
        if (!b2) {
            throw new GuliException(20001, "修改描述失败");
        }
    }

    @Override
    public CoursePublishVo getPublishCourseInfo(String courseId) {
        return baseMapper.getPublishCourseInfo(courseId);
    }

    @Override
    public boolean deleteCourse(String courseId) {

        //删除小节表
        if (!videoService.removeVideoByCourseId(courseId)) {
            throw new GuliException(20001, "删除课程小节失败");
        }
        //删除章节表
        if (!chapterService.removeChapterByCourseId(courseId)) {
            throw new GuliException(20001, "删除课程章节失败");
        }
        //删除课程描述表
        if (!descriptionService.removeById(courseId)) {
            throw new GuliException(20001, "删除课程描述失败");
        }
        //删除课程表
        if (!this.removeById(courseId)) {
            throw new GuliException(20001, "删除课程失败");
        }
        return true;
    }

    @Override
    public Map<String, Object> getCoursePage(Page<EduCourse> coursePage, CourseFrontVo vo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
//        System.out.println("impl:"+vo);
        //判断条件值是否为空
        if (vo != null) {
            if (!StringUtils.isBlank(vo.getSubjectCategoryId())) {//一级分类
                wrapper.eq("subject_parent_id", vo.getSubjectCategoryId());
            }
            if (!StringUtils.isBlank(vo.getSubjectId())) {//二级分类
                wrapper.eq("subject_id", vo.getSubjectId());
            }
            if (!StringUtils.isBlank(vo.getBuyCountSort())) {//关注度
                wrapper.orderByDesc("buy_count");
            }
            if (!StringUtils.isBlank(vo.getPriceSort())) {//价格
                wrapper.orderByDesc("price");
            }
            if (!StringUtils.isBlank(vo.getGmtCreateSort())) {//最新
                wrapper.orderByDesc("gmt_create");
            }
        }
        this.page(coursePage, wrapper);
        return PR.getPageMap(coursePage);
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }


}
