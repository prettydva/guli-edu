package com.trump.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.eduservice.service.EduSubjectService;
import com.trump.eduservice.entity.EduSubject;
import com.trump.eduservice.entity.excel.SubjectData;
import com.trump.eduservice.entity.subject.SubjectCategory;
import com.trump.eduservice.entity.subject.Subject;
import com.trump.eduservice.listener.SubjectListener;
import com.trump.eduservice.mapper.EduSubjectMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.eduservice.service.EduSubjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void addSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream inputStream = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(inputStream, SubjectData.class,new SubjectListener(subjectService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //查询课程分类列表（树形）
    @Override
    public List<SubjectCategory> getAllSubjects() {
        //1.查询出所有一级分类
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id", 0);
        List<EduSubject> list1 = baseMapper.selectList(wrapper1);

        //2.查询出所有二级分类
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper2.ne("parent_id", 0);
        List<EduSubject> list2 = baseMapper.selectList(wrapper2);

        //3.封装一级分类
        //一级分类目录集合
        List<SubjectCategory> categories = new ArrayList<>();

        for (int i = 0; i < list1.size(); i++) {
            //获取每个一级分类
            EduSubject sub1 = list1.get(i);
            //一级分类目录
            SubjectCategory category = new SubjectCategory();
            //把每个一级分类的属性封装到一级分类目录
            BeanUtils.copyProperties(sub1, category);

            //4.封装二级分类
            //二级分类目录集合
            List<Subject> subjects = new ArrayList<>();
            for (int j = 0; j < list2.size(); j++) {
                //获取每个二级分类
                EduSubject sub2 = list2.get(j);
                //判断二级分类的parentId和一级分类是否一样
                if(sub2.getParentId().equals(category.getId())){
                    //创建二级分类目录对象
                    Subject subject = new Subject();
                    //把每个二级分类的属性封装到二级分类目录
                    BeanUtils.copyProperties(sub2,subject);
                    //把二级分类目录添加到二级分类目录集合
                    subjects.add(subject);
                }
            }
            //把二级分类目录添加到一级分类目录
            category.setSubjects(subjects);
            //把一级分类目录添加到一级分类目录集合
            categories.add(category);
        }
        return categories;
    }
}
