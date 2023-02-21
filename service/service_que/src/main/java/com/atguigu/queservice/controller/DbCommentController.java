package com.atguigu.queservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.queservice.entity.DbComment;
import com.atguigu.queservice.service.DbCommentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 回复列表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@CrossOrigin
@RestController
@RequestMapping("/queservice/dbcomment")
public class DbCommentController {
    @Autowired
    private DbCommentService dbCommentService;
    //查询最佳答案
    @GetMapping("/getAnswerMvp/{id}")
    public R getAnswerMvp(@PathVariable("id") String id){
        QueryWrapper<DbComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.orderByDesc("zan");
        DbComment comment = dbCommentService.getOne(wrapper);
        return R.ok().data("MVPcomment",comment);
    }
    //根据问题id查询所有的回答
    @GetMapping("/getQuestList/{id}")
    public R getAnswerList(@PathVariable("id") String id){
        QueryWrapper<DbComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        List<DbComment> commentList = dbCommentService.list(wrapper);
        return R.ok().data("commentList",commentList);
    }

    //增加回答赞的方法
    @GetMapping("/addPraise/{answerId}")
    public R addPraise(@PathVariable("answerId") String answerId, HttpServletRequest request){
       int code= dbCommentService.addPraiseComment(answerId);
        return R.ok();
    }
}

