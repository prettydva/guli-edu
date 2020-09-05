package com.trump.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduVideoService;
import com.trump.eduservice.entity.EduChapter;
import com.trump.eduservice.entity.EduVideo;
import com.trump.eduservice.entity.vo.ChapterVo;
import com.trump.eduservice.entity.vo.VideoVo;
import com.trump.eduservice.mapper.EduChapterMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.trump.servicebase.exceptionhandler.GuliException;
import com.trump.eduservice.service.EduChapterService;
import com.trump.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Trump
 * @since 2020-08-17
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService videoService;

    //根据课程id查询课程大纲列表
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1.根据课程id查询课程里的所有章节
        QueryWrapper<EduChapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<EduChapter> chapterList = baseMapper.selectList(chapterWrapper);

        //2.根据课程id查询课程里的所有小节
        QueryWrapper<EduVideo> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id", courseId);
        List<EduVideo> videoList = videoService.list(videoWrapper);

        //3.遍历封装所有章节
        //章节vo集合
        List<ChapterVo> chapterVoList = new ArrayList<>();

        for (int i = 0; i < chapterList.size(); i++) {
            //得到每个章节
            EduChapter chapter = chapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            //EduChapter对象值赋值到ChapterVo
            BeanUtils.copyProperties(chapter,chapterVo);
            //遍历小节
            //小节vo集合
            List<VideoVo> videoVoList = new ArrayList<>();
            for (int j = 0; j < videoList.size(); j++) {
                //得到每个小节
                EduVideo video = videoList.get(j);
                //判断小节里面的ChapterId和chapter里的id是否一样
                if(video.getChapterId().equals(chapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    //把小节vo封装到小节vo集合
                    videoVoList.add(videoVo);
                }
            }
            //把小节vo集合封装到章节vo
            chapterVo.setVideos(videoVoList);
            //把章节vo集合封装到章节vo集合
            chapterVoList.add(chapterVo);
        }
        return chapterVoList;
    }

    //删除章节
    @Override
    public boolean deleteChapter(String chapterId) {
        //先查询小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", chapterId);
        int count = videoService.count(wrapper);
        if (count>0){
            throw new GuliException(20001,"该章节存在小节，无法删除");
        }else{
            return this.removeById(chapterId);
        }
    }

    @Override
    public boolean removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        return this.remove(wrapper);
    }
}
