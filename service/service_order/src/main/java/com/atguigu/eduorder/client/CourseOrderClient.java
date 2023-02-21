package com.atguigu.eduorder.client;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduorder.client.impl.CourseOrderFileDegradFeignClient;
import com.atguigu.eduorder.client.impl.UcenterOrderFileDegradFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-edu",fallback = CourseOrderFileDegradFeignClient.class)

public interface CourseOrderClient {
    //根据课程id查询课程信息
    @GetMapping("/eduservice/coursefront/getCourseInfoOrder/{courseId}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("courseId") String courseId);
}
