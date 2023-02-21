package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */

public interface EduCourseMapper extends BaseMapper<EduCourse> {
public CoursePublishVo getPublishCourseInfo(String courseId);
//根据课程id删除课程
public void   deleteCourseById(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
