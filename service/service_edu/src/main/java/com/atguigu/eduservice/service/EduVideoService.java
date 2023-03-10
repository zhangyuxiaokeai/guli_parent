package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
public interface EduVideoService extends IService<EduVideo> {

    List<EduVideo> getVideoByChaptId(String chapterId);

    void removeDeleteByCourseId(String courseId);
}
