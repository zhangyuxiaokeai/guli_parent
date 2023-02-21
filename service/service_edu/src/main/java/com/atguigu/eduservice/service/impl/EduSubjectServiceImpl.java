package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-08-31
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        InputStream in=null;
        try {
            //文件输入流
          in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //课程分类列表
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //构建条件查询一级分类
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapper);
        //构建条件查询二级分类
        QueryWrapper<EduSubject> wrappertwo = new QueryWrapper<>();
        wrapper.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrappertwo);

        //构建一个数组,封装一级分类list集合遍历，得到每一个一级分类对象，获取每一个一级分类对象值
        //封装到要求的list集合里面 List<OneSubject> finalSubject
        List<OneSubject> finalSubjects = new ArrayList<>();
        for (int i = 0; i < oneSubjectList.size(); i++) {
            //获取oneSubject的每个对象值
            EduSubject eduSubject = oneSubjectList.get(i);
            OneSubject oneSubject = new OneSubject();
            //两种方法  （一种就是你想的new对象方法，这里写第二种）
            //将一个对象的值封装到另一个对象
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finalSubjects.add(oneSubject);


            //创建一个llist集合封装每一个一级分类的二级分类
            List <TwoSubject> twoFinalSubjectList=new ArrayList<>();
            //遍历二级分类list集合
            for (int j = 0; j < twoSubjectList.size(); j++) {
                //获取每个二级分类
                EduSubject tSubject1 = twoSubjectList.get(j);
                //判断二级分类parentid和一级分类的id
                if(tSubject1.getParentId().equals(eduSubject.getId())){
                    //把tsubject的值复制到twoSubjectList中去，放到twoFinalSubjectList中去
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(tSubject1,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinalSubjectList);
        }

        return finalSubjects;
    }
}
