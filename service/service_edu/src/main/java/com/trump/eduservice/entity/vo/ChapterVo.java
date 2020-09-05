package com.trump.eduservice.entity.vo;

import lombok.Data;

import java.util.List;

@Data
public class ChapterVo {
    private String id;

    private String title;
    //表示小节
    private List<VideoVo> videos;
}
