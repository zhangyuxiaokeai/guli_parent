package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private EduCourseService eduCourseService;

    //条件查询带分页的方法
    @PostMapping("/getFrontCourseList/{current}/{limit}")
    public R getFrontCourseList(@PathVariable long current,
                                @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> frontVoPage = new Page<>(current,limit);
       Map<String,Object> map=eduCourseService.getCourseFrontList(frontVoPage,courseFrontVo);
        return R.ok().data("map",map);
    }
    //查询课程详情的方法
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId){
        //根据课程id，编写sql语句查询课程信息
      CourseWebVo courseWebVo=eduCourseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小结
    List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
    return R.ok().data("courseWebVo",courseWebVo).data("chapterVideoByCourseId",chapterVideoByCourseId);
}
    //根据课程id查询课程信息
    @GetMapping("getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String courseId){
        CourseInfoVo courseInfo = eduCourseService.getCourseInfo(courseId);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
