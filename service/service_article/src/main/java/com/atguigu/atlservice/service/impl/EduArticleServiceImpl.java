package com.atguigu.atlservice.service.impl;

import com.atguigu.atlservice.entity.EduArticle;
import com.atguigu.atlservice.entity.EduContent;
import com.atguigu.atlservice.entity.vo.ArticleVo;
import com.atguigu.atlservice.mapper.EduArticleMapper;
import com.atguigu.atlservice.service.EduArticleService;
import com.atguigu.atlservice.service.EduContentService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-10-26
 */
@Service
public class EduArticleServiceImpl extends ServiceImpl<EduArticleMapper, EduArticle> implements EduArticleService {
    @Autowired
    private EduContentService eduContentService;

    //根据文章id点赞
    @Override
    public void updatePraiseById(String id) {
        baseMapper.updatePraiseById(id);
    }
    //获取文章列表带分页
    @Override
    public Map<String, Object> getArticleMap(Page<EduArticle> page) {
        QueryWrapper<EduArticle> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        baseMapper.selectPage(page,wrapper);
        List<EduArticle> records = page.getRecords();
        long current = page.getCurrent();
        long pages = page.getPages();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();//是否有下一页
        boolean hasPrevious = page.hasPrevious();//是否有上一页
        Map<String, Object> map = new HashMap<>();
        map.put("records",records);
        map.put("current",current);
        map.put("pages",pages);
        map.put("size",size);
        map.put("total",total);
        map.put("hasNext",hasNext);
        map.put("hasPrevious",hasPrevious);
        return map;
    }

    @Override
    public List<EduArticle> getHotArticle() {
        //获取热门文章排行
        QueryWrapper<EduArticle> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("praise");
        wrapper.last("limit 8");
        List<EduArticle> eduArticleList = baseMapper.selectList(wrapper);
        return eduArticleList;
    }
    //获取文章详情页数据
    @Override
    public ArticleVo getArticleDetail(String id) {
        EduArticle eduArticle = baseMapper.selectById(id);
        String detailId=eduArticle.getTextId();
        EduContent content = eduContentService.getById(detailId);
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(eduArticle,articleVo);
        String comment = content.getContent();
        articleVo.setContent(comment);
        return articleVo;
    }
    //增加文章的方法
    @Override
    public void addArticle(ArticleVo articleVo) {
        //向文章列表中添加基本信息
        String title = articleVo.getTitle();
        String abstractl = articleVo.getAbstractl();
        String author = articleVo.getAuthor();
        String avater = articleVo.getAvater();
        String content = articleVo.getContent();

        EduArticle eduArticle = new EduArticle();
        eduArticle.setTitle(title);
        eduArticle.setAbstractl(abstractl);
        eduArticle.setAuthor(author);
        eduArticle.setAvater(avater);

        EduContent eduContent = new EduContent();
        eduContent.setContent(content);
        boolean save = eduContentService.save(eduContent);

        if (save==false) {
        throw new GuliException(20001,"添加文章信息失败");
        }
        eduArticle.setTextId(eduContent.getId());
        baseMapper.insert(eduArticle);
    }
}
