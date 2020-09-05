package com.trump.eduservice.service;

import com.trump.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduservice.entity.subject.SubjectCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
public interface EduSubjectService extends IService<EduSubject> {

    void addSubject(MultipartFile file,EduSubjectService subjectService);

    List<SubjectCategory> getAllSubjects();
}
