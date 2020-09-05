package com.trump.eduservice.service;

import com.trump.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.trump.eduservice.entity.vo.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);

    boolean removeChapterByCourseId(String courseId);
}
