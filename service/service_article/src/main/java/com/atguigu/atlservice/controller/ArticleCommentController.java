package com.atguigu.atlservice.controller;


import com.atguigu.atlservice.entity.ArticleComment;
import com.atguigu.atlservice.service.ArticleCommentService;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-30
 */
@CrossOrigin
@RestController
@RequestMapping("/atlservice/comment")
public class ArticleCommentController {
    @Autowired 
    private ArticleCommentService articleCommentService;
    //增加课程评论的方法
    @PostMapping("addComment")
    public R addComment(@RequestBody ArticleComment articleTo, HttpServletRequest request){
        System.out.println(articleTo.toString());
        String memberId=JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(memberId);
        if(StringUtils.isEmpty(articleTo.getComments())){
            throw new GuliException(20001,"请填写评论后在点击");
        }
        if(StringUtils.isEmpty(memberId)){
            throw new GuliException(20001,"请登录再发评论喔");
        }
        System.out.println(memberId);
        boolean ok = articleCommentService.addComment(articleTo, memberId);
        return R.ok();
    }
    //查看评论列表的方法，带分页显示
    @GetMapping("/getCommentList/{id}")
    public R getComment(@PathVariable String id){
        QueryWrapper<ArticleComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id",id);
        List<ArticleComment> list = articleCommentService.list(queryWrapper);
        int commentSize = list.size();
        return R.ok().data("list",list).data("commentSize",commentSize);
    }
}

