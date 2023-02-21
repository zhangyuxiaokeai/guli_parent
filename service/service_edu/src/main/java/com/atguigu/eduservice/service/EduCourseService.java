package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);
    //根据id返回课程的基本信息
    CourseInfoVo getCourseInfo(String courseId);
    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);
    //根据id确认信息查询publisCourse的课程信息
    CoursePublishVo publishCourseInfo(String id);
    //删除课程
    void deleteCourseId(String courseId);

    List<EduCourse> selectHotCourse();
    //条件查询带分页
    Map<String, Object> getCourseFrontList(Page<EduCourse> frontVoPage ,CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);

    //根据课程id删除课程
    //void deleteCourseById(String courseid);
}
