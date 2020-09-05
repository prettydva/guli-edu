package com.trump.eduservice.entity.subject;

import lombok.Data;

import java.util.List;

//一级分类
@Data
public class SubjectCategory {
    private String id;
    private String title;
    private List<Subject> subjects;
}
