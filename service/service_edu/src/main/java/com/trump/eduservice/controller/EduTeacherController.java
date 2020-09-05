package com.trump.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduTeacherService;
import com.trump.commonutils.PR;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.vo.TeacherQuery;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-14
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {
    @Autowired
    private EduTeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/getAll")
    public R getAllTeacher() {
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data(list);
    }

    //逻辑删除讲师
    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam("讲师id") @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //分页模糊查询讲师
    @PostMapping("/pageTeacher/{currentPage}/{pageSize}")
    public R getTeacherList(@PathVariable long currentPage,
                            @PathVariable long pageSize,
                            @RequestBody(required = false) TeacherQuery teacherQuery) {
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(currentPage, pageSize);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //判断条件是否为空，如果不为空则拼接条件
        if (teacherQuery != null ) {
            String name = teacherQuery.getName();
            Integer level = teacherQuery.getLevel();
            String begin = teacherQuery.getBegin();
            String end = teacherQuery.getEnd();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (level != null) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            if (!StringUtils.isEmpty(end)) {
                wrapper.le("gmt_modified", end);
            }
        }
        //按创建时间倒序排序
        wrapper.orderByDesc("gmt_create");
        //调用方法实现分页，底层封装，把分页所有数据封装到pageTeacher对象里
        teacherService.page(pageTeacher, wrapper);
        //把总记录数和分页数据封装到分页结果对象
        PR<EduTeacher> pr = new PR<>(pageTeacher.getTotal(), pageTeacher.getRecords());
        //把分页结果对象添加到结果对象
        return R.ok().data(pr);
    }

    //添加讲师
    @PostMapping("/add")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id查询
    @GetMapping("/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data(teacher);
    }

    //修改讲师
    @ApiOperation("修改讲师")
    @PutMapping("/{id}")
    public R updateTeacher(
            @ApiParam("讲师id")
            @PathVariable String id,
            @ApiParam("讲师对象")
            @RequestBody EduTeacher teacher) {
        teacher.setId(id);
        boolean flag = teacherService.updateById(teacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

}

