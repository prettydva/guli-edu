package com.trump.commonutils.ordervo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class CourseVoOrder {
    @ApiModelProperty(value = "课程id")
    private String id;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
}

