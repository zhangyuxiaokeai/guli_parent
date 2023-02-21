package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    //分页查询课程的方法
    @GetMapping("/pageCourse/{current}/{limit}")
    public R pageCourse(@PathVariable long current,
                        @PathVariable long limit) {
        //1.创建一个page对象
        Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页数据分装到pageTeacher对象里面去
        eduCourseService.page(eduCoursePage, null);
        //总记录数
        long total = eduCoursePage.getTotal();
        //返回list集合
        List<EduCourse> records = eduCoursePage.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //条件查询
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current,
                                 @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery) {

        //1.创建一个page对象
        Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        //2.创造构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //实现对条件组合查询
        //判断条件值是否为空，如果不为空拼接条件
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件是否为空，如果不为空拼接条件
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(status)) {
            wrapper.like("status", status);
        }
        wrapper.orderByDesc("cover");

        //调用方法实现分页查询
        eduCourseService.page(eduCoursePage, wrapper);
        //获取总记录数
        long total = eduCoursePage.getTotal();
        List<EduCourse> records = eduCoursePage.getRecords();//返回list集合
        return R.ok().data("total", total).data("rows", records);
    }


    //添加课程基本信息
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId", id);
    }

    //根据id查询信息
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("getCourseInfo", courseInfoVo);
    }

    //根据id修改课程基本信息
    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        System.out.println(courseInfoVo.toString());
        eduCourseService.updateCourseInfo(courseInfoVo);

        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("/getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse", coursePublishVo);
    }

    //最终发布课程
    @PostMapping("/publish/{id}")
    public R publish(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")                                   //1567871107816546305
    public R deleteCourse(@PathVariable String courseId) {
         eduCourseService.deleteCourseId(courseId);
         return R.ok();
    }

}