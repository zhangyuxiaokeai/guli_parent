package com.atguigu.atlservice.controller;


import com.atguigu.atlservice.entity.EduArticle;
import com.atguigu.atlservice.entity.vo.ArticleVo;
import com.atguigu.atlservice.service.EduArticleService;
import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/atlservice/article")
@CrossOrigin
public class EduArticleController {
    @Autowired
    private EduArticleService eduArticleService;
    //获取文章列表
    @GetMapping("/getList/{current}/{limit}")
    public R getList(@PathVariable long current,
                     @PathVariable long limit){
        //创建一个page对象对查询到的数据进行分页处理
        Page<EduArticle> articlePagepage = new Page<>(current,limit);
        //将查询到的数据封装到page对象中
        Map<String, Object> map = eduArticleService.getArticleMap(articlePagepage);
        return R.ok().data("map",map);
    }
    //赞文章
    @GetMapping("/addPraise/{id}")
    public R addPraise(@PathVariable String id, HttpServletRequest request){
    //当用户点击前端页面后台文章的赞加一
            String tokenId = JwtUtils.getMemberIdByJwtToken(request);
            if(tokenId!=""){
            System.out.println(tokenId);
            eduArticleService.updatePraiseById(id);
            System.out.println("321");
            return R.ok();
            }else{
               throw  new GuliException(20001,"请登录");
            }
    }
    //获取文章排行
    @GetMapping("/getHotList")
    public R getHotList(){
        //获取热门文章排行
       List<EduArticle> hotList=eduArticleService.getHotArticle();
       return R.ok().data("hotList",hotList);
    }
    //查询文章详情页面接口
    @GetMapping("/getarticleDetail/{id}")
    public R getArticle(@PathVariable String id){
       ArticleVo articleVo= eduArticleService.getArticleDetail(id);
       return R.ok().data("articleVo",articleVo);
    }
    //增加文章的方法
    @PostMapping("/addarticle")
    public R addArticle(@RequestBody ArticleVo articleVo){
        eduArticleService.addArticle(articleVo);
        return R.ok();
    }
}

