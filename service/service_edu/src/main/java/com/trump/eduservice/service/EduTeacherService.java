package com.trump.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduservice.entity.EduTeacher;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-14
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTacherFrontPage(Page<EduTeacher> teacherPage);
}
