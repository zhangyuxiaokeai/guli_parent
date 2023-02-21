package com.atguigu.queservice.controller;


import com.atguigu.atlservice.client.UcenterClient;
import com.atguigu.atlservice.entity.EduArticle;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.queservice.entity.DbComment;
import com.atguigu.queservice.entity.DbQuest;
import com.atguigu.queservice.entity.vo.DbQuestVo;
import com.atguigu.queservice.service.DbCommentService;
import com.atguigu.queservice.service.DbQuestService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问题列表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/queservice/dbquest")
public class DbQuestController {
    @Autowired
    private DbQuestService dbQuestService;
    @Autowired
    private DbCommentService dbCommentService;
    @Autowired
    private UcenterClient ucenterClient;
    //增加问题
    @PostMapping("/addquestion")
    public R addComment(@RequestBody DbQuest dbQuest, HttpServletRequest request){
        System.out.println(dbQuest);
        String memberId= JwtUtils.getMemberIdByJwtToken(request);
        System.out.println(memberId);
        if(memberId==null){
            throw new GuliException(20001,"请登录后在发表问题");
        }
        UcenterMemberOrder memberOrder = ucenterClient.getInfoUc(memberId);
        String avatar = memberOrder.getAvatar();
        String nickname = memberOrder.getNickname();
        String userId = memberOrder.getId();
        log.info(userId);
        dbQuest.setNickname(nickname);
        dbQuest.setAvatar(avatar);
        dbQuest.setUserId(userId);
        dbQuest.setZan(0);
        dbQuest.setViews(0);
        dbQuest.setCourseId("0");
        System.out.println(dbQuest.getUserId());
        boolean save = dbQuestService.save(dbQuest);
        System.out.println(save);
        return R.ok();
    }

    //获取问题列表
    @GetMapping("/getquestion")
    public R getList(){
        List<DbQuest> questionList = dbQuestService.list(null);
        List<DbQuestVo> dbQuestVoList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            DbQuestVo dbQuestVo = new DbQuestVo();
            //将问题一个一个拿出来
            DbQuest dbQuest = questionList.get(i);
            //根据问题id查询问题的最佳答案
            QueryWrapper<DbComment> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", dbQuest.getId());
            wrapper.orderByDesc("zan");
            DbComment dbComment = dbCommentService.getOne(wrapper);
            if(dbComment!=null){
                //获取回答时间
                //获取回答时间
                String answer = dbComment.getAnswer();
                Date gmtCreate = dbComment.getGmtCreate();
                String answerNickname = dbComment.getNickname();
                dbQuestVo.setAnswer(answer);
                dbQuestVo.setAnswerNickname(answerNickname);
                dbQuestVo.setAnswerTime(gmtCreate);
            }
            dbQuestVo.setId(dbQuest.getId());
            dbQuestVo.setUserId(dbQuest.getUserId());
            dbQuestVo.setNickname(dbQuest.getNickname());
            dbQuestVo.setAvatar(dbQuest.getAvatar());
            dbQuestVo.setTitle(dbQuest.getTitle());
            dbQuestVo.setDescribes(dbQuest.getDescribes());
            dbQuestVo.setViews(dbQuest.getViews());
            dbQuestVo.setZan(dbQuest.getZan());
            dbQuestVo.setGmtCreate(dbQuest.getGmtCreate());
            dbQuestVoList.add(dbQuestVo);
        }
        return R.ok().data("dbQuestVoList",dbQuestVoList);
    }

    //增加问题赞的方法
    @GetMapping("/addPraise/{id}")
    public R addPraise(@PathVariable String id, HttpServletRequest request){
        //当用户点击前端页面后台文章的赞加一
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            throw new GuliException(20001,"请登录");
        }
        int i = dbQuestService.addPrase(id);
        System.out.println("方法执行到这里");
        return R.ok();
    }

    //获取热门问答列表
    @GetMapping("/getHotQuestion")
    public R getHotList(){
        QueryWrapper<DbQuest> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("zan");
        wrapper.last("limit 8");
        List<DbQuest> hotList = dbQuestService.list(wrapper);
        return R.ok().data("hotList",hotList);
    }
    //获取问答详情
    @GetMapping("/getQuestionDetail/{id}")
    public R getQuestionDetail(@PathVariable("id") String id){
        DbQuestVo dbQuestVo = new DbQuestVo();
        //获取问题详情
        DbQuest question = dbQuestService.getById(id);
        //将问题封装到vo类中
        dbQuestVo.setId(question.getId());
        dbQuestVo.setUserId(question.getUserId());
        dbQuestVo.setNickname(question.getNickname());
        dbQuestVo.setAvatar(question.getAvatar());
        dbQuestVo.setTitle(question.getTitle());
        dbQuestVo.setDescribes(question.getDescribes());
        dbQuestVo.setViews(question.getViews());
        dbQuestVo.setZan(question.getZan());
        dbQuestVo.setGmtCreate(question.getGmtCreate());
        //获取最佳答案
        QueryWrapper<DbComment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.orderByDesc("zan");
        DbComment comment = dbCommentService.getOne(wrapper);
        //将最佳答案封装到vo类中
        if(comment==null){
            return R.ok().data("dbQuestVo",dbQuestVo);
        }
        dbQuestVo.setAnswerNickname(comment.getNickname());
        dbQuestVo.setAnswer(comment.getAnswer());
        dbQuestVo.setAnswerTime(comment.getGmtCreate());
        dbQuestVo.setAnswerId(comment.getId());
        dbQuestVo.setAvatarComment(comment.getAvatar());
        dbQuestVo.setCommentZan(comment.getZan());
        return R.ok().data("dbQuestVo",dbQuestVo);
    }
}

