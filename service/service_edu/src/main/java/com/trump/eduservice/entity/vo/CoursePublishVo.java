package com.trump.eduservice.entity.vo;

import lombok.Data;

@Data
public class CoursePublishVo {
    private String id;
    private String title;
    private String cover;
    private String description;
    private Integer lessonNum;
    private String subjectCategory;
    private String subject;
    private String teacherName;
    private String price;
}
