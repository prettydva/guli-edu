package com.trump.eduservice.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.eduservice.service.EduSubjectService;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.eduservice.entity.EduSubject;
import com.trump.eduservice.entity.excel.SubjectData;
import com.trump.eduservice.service.EduSubjectService;

public class SubjectListener extends AnalysisEventListener<SubjectData> {
    //因为SubjectListener不能交给spring进行管理,需要自己new,不能注入其他对象

    private EduSubjectService subjectService;

    public SubjectListener() {

    }

    public SubjectListener(EduSubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //一行一行读取excel内容
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if (subjectData == null) {
            throw new GuliException(20001, "文件数据为空");
        }
        System.out.println("***subjectData:"+subjectData);
        //一行一行读取，每次读取有两个值，第一个值一级分类，第二个值二级分类
        //判断一级分类是否重复
        String categoryName = subjectData.getSubjectCategory();
        EduSubject subjectCategory = this.existSubjectCategory(subjectService, categoryName);
        if (subjectCategory == null) {//没有相同一级分类，进行添加
            subjectCategory = new EduSubject();
            subjectCategory.setParentId("0");
            subjectCategory.setTitle(categoryName);
            subjectService.save(subjectCategory);

        }
        //获取一级分类id值
        String pid = subjectCategory.getId();
        //添加二级分类
        String subjectName = subjectData.getSubjectName();
        //判断二级分类是否重复
        EduSubject subject = this.existSubject(subjectService,subjectName,pid);
        if (subject==null){
            subject = new EduSubject();
            subject.setTitle(subjectName);
            subject.setParentId(pid);
            subjectService.save(subject);
        }

    }

    //判断一级分类不能重复添加
    private EduSubject existSubjectCategory(EduSubjectService subjectService, String name) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", 0);
        return subjectService.getOne(wrapper);
    }

    //判断二级分类不能重复添加
    private EduSubject existSubject(EduSubjectService subjectService, String name, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", name);
        wrapper.eq("parent_id", pid);
        return subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
