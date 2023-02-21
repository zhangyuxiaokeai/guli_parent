package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduCourseDescription;
import com.atguigu.eduservice.entity.frontVo.CourseFrontVo;
import com.atguigu.eduservice.entity.frontVo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.mapper.EduCourseMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduCourseDescriptionService;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-09-01
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    //注入课程藐视
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Autowired
    private EduVideoService eduVideoService;
    @Autowired
    private EduChapterService eduChapterService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表中加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
       // boolean save = this.save(eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if (insert==0){
            throw new GuliException(20001,"添加课程信息失败");
        }
        //获取id
        String cid= eduCourse.getId();
        //向课程简介表中添加信息
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
       //手动设置描述id就是课程id
        eduCourseDescription.setId(cid);
        boolean save = eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //创建一个CourseInfoVo实体类，用于封装查询到的信息
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        //查询课程的基本信息
        EduCourse eduCourse = baseMapper.selectById(courseId);
        //封装查询到的eduCourse中的信息
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        //查询课程的描述信息
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //先将Vo中的值赋值给EduCourse 在根据id修改
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update==0){
            throw  new GuliException(20001,"修改失败");
        }
        //先将Vo中的值赋值给EduCourseDescriPtion在根据id修改
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
    @Transactional
    @Override
    public void deleteCourseId(String courseId) {
        //1删除小结，根据课程id
       eduVideoService.removeDeleteByCourseId(courseId);
       //3.根据课程id删除章节
        eduChapterService.removeDeleteByCourseId(courseId);
        //3.根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);
        //3删除课程,根据课程id

int result = baseMapper.deleteById(courseId);
        if(result==0){
            throw  new GuliException(20001,"删除失败");
        }
        System.out.println(result);
    }
    @Cacheable(value = "hotCourse",key = "'List'")
    @Override
    public List<EduCourse> selectHotCourse() {
        QueryWrapper<EduCourse> eduCourseQueryWrapper = new QueryWrapper<>();
        eduCourseQueryWrapper.orderByDesc("id");
        eduCourseQueryWrapper.last("limit 8");
        List<EduCourse> eduCourses = baseMapper.selectList(eduCourseQueryWrapper);
        return eduCourses;
    }

    //条件查询带分页
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageParam,CourseFrontVo courseFrontVo) {
        //根据讲师id查询所有课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            //一级分类是否为空
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            //判断二级分类是否为空
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            //判断关注度是否为空
            wrapper.orderByDesc("buy_count",courseFrontVo.getBuyCountSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            //判断创建时间是否为空
            wrapper.orderByDesc("gmt_create",courseFrontVo.getGmtCreateSort());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            //判断价格
            wrapper.orderByDesc("price",courseFrontVo.getPriceSort());
        }
         baseMapper.selectPage(pageParam, wrapper);
        List<EduCourse> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();//是否有下一页
        boolean hasPrevious = pageParam.hasPrevious();//是否有上一页

        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        //把分页的数据取出来，放到map集合


        return map;
    }

    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return baseMapper.getBaseCourseInfo(courseId);
    }
}
