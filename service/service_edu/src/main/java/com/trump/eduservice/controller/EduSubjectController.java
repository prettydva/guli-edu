package com.trump.eduservice.controller;


import com.trump.eduservice.service.EduSubjectService;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.subject.SubjectCategory;
import com.trump.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        subjectService.addSubject(file,subjectService);
        return R.ok();
    }

    //课程分类列表（树形）
    @GetMapping("/getAllSubjects")
    public R getAllSubjects(){
        List<SubjectCategory> list = subjectService.getAllSubjects();
        return R.ok().data(list);
    }
}

