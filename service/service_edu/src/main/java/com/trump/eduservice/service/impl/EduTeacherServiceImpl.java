package com.trump.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.mapper.EduTeacherMapper;
import com.trump.eduservice.service.EduTeacherService;
import com.trump.commonutils.PR;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.eduservice.entity.EduTeacher;
import com.trump.eduservice.mapper.EduTeacherMapper;
import com.trump.eduservice.service.EduTeacherService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-14
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTacherFrontPage(Page<EduTeacher> teacherPage) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        this.page(teacherPage, wrapper);
        return PR.getPageMap(teacherPage);
    }


}
