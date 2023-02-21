package com.atguigu.demo;

import com.atguigu.eduservice.service.EduCourseService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class testdelete {
    @Autowired
    private EduCourseService eduCourseService;

   @Test
    public void test1(){
       eduCourseService.deleteCourseId("123");
   }
}
