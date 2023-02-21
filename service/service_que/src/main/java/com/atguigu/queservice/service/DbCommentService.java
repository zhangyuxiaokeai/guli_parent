package com.atguigu.queservice.service;

import com.atguigu.queservice.entity.DbComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 回复列表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
public interface DbCommentService extends IService<DbComment> {

    int addPraiseComment(String answerId);
}
