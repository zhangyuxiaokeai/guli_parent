package com.atguigu.eduorder.client.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.eduorder.client.CourseOrderClient;
import org.springframework.stereotype.Component;

@Component
public class CourseOrderFileDegradFeignClient implements CourseOrderClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String courseId) {
        return null;
    }
}
