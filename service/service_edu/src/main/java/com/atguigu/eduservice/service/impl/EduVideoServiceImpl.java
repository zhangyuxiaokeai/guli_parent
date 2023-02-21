package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sun.xml.bind.v2.TODO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
//    注入vodClicent
    @Autowired
    private VodClient vodClient;


    @Override
    public List<EduVideo> getVideoByChaptId(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        List<EduVideo> eduVideos = baseMapper.selectList(wrapper);
        return eduVideos;
    }

    /**
     * TODO//删除小结删除对应视频
     * @param courseId
     */
    @Override
    public void removeDeleteByCourseId(String courseId) {
        //根据课程id查出所有的视频id
        //设置查询条件
        QueryWrapper<EduVideo> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        queryWrapper.select("video_source_id");
        //查询返回list集合
        List<EduVideo> eduVideoList = baseMapper.selectList(queryWrapper);
        // 将List<EduVideo>变成list<String>
        List<String> listVideo=new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            if(!StringUtils.isEmpty(eduVideoList.get(i).getVideoSourceId())){
                //videoID放入到listVideo中
                listVideo.add(eduVideoList.get(i).getVideoSourceId());
            }
        }
        //如果listVideo里面没有值就没必要调用里面的方法
        if(listVideo.size()>0){
            //调用vod_client中的方法
            vodClient.deleteBatch(listVideo);
        }
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
