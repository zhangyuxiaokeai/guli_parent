package com.atguigu.atlservice.service;

import com.atguigu.atlservice.entity.EduArticle;
import com.atguigu.atlservice.entity.vo.ArticleVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-10-26
 */
public interface EduArticleService extends IService<EduArticle> {
    //点赞
    void updatePraiseById(String id);
    //获取文章列表并分页
    Map<String, Object> getArticleMap(Page<EduArticle> page);
    //获取热门文章排行
    List<EduArticle> getHotArticle();
    //获取文章详情页
    ArticleVo getArticleDetail(String id);
    //增加文章的方法
    void addArticle(ArticleVo articleVo);
}
