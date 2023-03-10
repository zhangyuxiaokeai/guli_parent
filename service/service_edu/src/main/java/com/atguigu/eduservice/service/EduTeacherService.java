package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-08-26
 */
public interface EduTeacherService extends IService<EduTeacher> {
    //查询热门名师
    List<EduTeacher> selectHotTeacher();

    //查询名列表
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> teacherPage);
}
