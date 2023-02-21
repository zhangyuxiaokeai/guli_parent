package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexFront")
@CrossOrigin
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

@GetMapping("/index")
    public R index(){
    //查询前八条热门课程 查询前四位名师
    List<EduCourse> listCourse=eduCourseService.selectHotCourse();

    //List<EduCourse> listCourse = eduCourseService.list(eduCourseQueryWrapper);

    //查询前四的名师
  List<EduTeacher> eduTeachers=eduTeacherService.selectHotTeacher();
    return R.ok().data("listCourse",listCourse).data("eduTeachers",eduTeachers);
}
}
