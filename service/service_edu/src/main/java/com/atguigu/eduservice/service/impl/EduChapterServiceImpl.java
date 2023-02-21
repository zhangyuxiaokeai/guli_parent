package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @author testjava
 * @since 2022-09-01
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    private EduVideoService eduVideoService;
    @Override
    //查新所有章节
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        QueryWrapper<EduChapter> chapterwrapper = new QueryWrapper<>();
        chapterwrapper.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterwrapper);

    //分局id查询课程里面的所有小结
        QueryWrapper<EduVideo> eduVideoQueryWrapper = new QueryWrapper<>();
        eduVideoQueryWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(eduVideoQueryWrapper);

        //创建一个list<chaptVo>的list
        List<ChapterVo> chapterVos = new ArrayList<>();
        //遍历所有章节进行封装
        for (int i = 0; i < eduChapterList.size(); i++) {
            //获取list集合中的每个对象
            EduChapter eduChapter = eduChapterList.get(i);
            //获取章节id便于和小节中的id进行对比
            String cid = eduChapter.getId();
            ChapterVo chapterVo = new ChapterVo();
            //将chapter的值封装到chaptervo中
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //添加到章节list
            chapterVos.add(chapterVo);
            //创建一个数组用于封装小结目录
            List<VideoVo> videoVos = new ArrayList<>();
            for (int j = 0; j < eduVideoList.size(); j++) {
                //获取小结对象
                EduVideo eduVideo = eduVideoList.get(j);
                //判断小节中的ChapterId是否和章节id对应
                if(eduVideo.getChapterId().equals(cid)){
                    //创建一个videoVo对象用于封装EduVideo订单值
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videoVos.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVos);
        }


        return chapterVos;
    }

    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id查询小节表，如果查询到数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        //判断

        if(count>0){//可以查询导数据不进行删除
            throw new GuliException(20001,"不能删除");
        }else {
            //没有查询出数据，进行删除
            int result = baseMapper.deleteById(chapterId);
            return  result>0;
        }
    }

    @Override
    public void removeDeleteByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);

    }
}
