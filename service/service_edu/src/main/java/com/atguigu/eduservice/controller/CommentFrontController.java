package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.netflix.client.http.HttpRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/eduservice/comment")
public class CommentFrontController {
    @Autowired
    private EduCommentService eduCommentService;
    @Autowired
    private UcenterClient ucenterClient;

    //根据课程id查询评论列表
    @GetMapping("/getComment/{current}/{limit}/{courseId}")
    public R getComment(@PathVariable long current,
                        @PathVariable long limit,
                        @PathVariable String courseId){
        Page<EduComment> eduCommentPage = new Page<>(current,limit);
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        eduCommentService.page(eduCommentPage, wrapper);
        List<EduComment> records = eduCommentPage.getRecords();
        Map<String, Object> map = new HashMap<>();
         map.put("records", records);
         map.put("current",eduCommentPage.getCurrent());
         map.put("pages",eduCommentPage.getPages());
         map.put("size",eduCommentPage.getSize());
         map.put("total",eduCommentPage.getTotal());
         map.put("hasNext",eduCommentPage.hasNext());
         map.put("hasPrevious",eduCommentPage.hasPrevious());
         return R.ok().data("map",map);
    }
    //根据客户id查询用户信息，并添加评论
    @ApiOperation(value = "添加评论")
    @PostMapping("/saveComment")
    public R saveComment(@RequestBody EduComment comment, HttpServletRequest request){
        //取出token里面的用户信息
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().code(28004).message("请登录");
        }
        UcenterMemberOrder member = ucenterClient.getInfoUc(memberId);
        comment.setAvatar(member.getAvatar());
        comment.setMemberId(member.getId());
        comment.setNickname(member.getNickname());
        eduCommentService.save(comment);
        return R.ok();
    }

}
