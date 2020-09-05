package com.trump.eduservice.controller;


import com.trump.eduservice.service.EduChapterService;
import com.trump.commonutils.R;
import com.trump.eduservice.entity.EduChapter;
import com.trump.eduservice.entity.vo.ChapterVo;
import com.trump.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Autowired
    private EduChapterService chapterService;

    //根据课程id查询课程大纲列表
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data(list);
    }

    //添加章节
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    //根据id查询章节
    @GetMapping("/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter chapter = chapterService.getById(chapterId);
        return R.ok().data(chapter);
    }

    //修改章节
    @PutMapping("/{chapterId}")
    public R updateChapter(@PathVariable String chapterId,
                            @RequestBody EduChapter chapter){
        chapter.setId(chapterId);
        chapterService.updateById(chapter);
        return R.ok();
    }

    //删除章节
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }
}

