package com.atguigu.atlservice.service;

import com.atguigu.atlservice.entity.ArticleComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-10-30
 */
public interface ArticleCommentService extends IService<ArticleComment> {

    boolean addComment(ArticleComment comment, String memberId);
}
