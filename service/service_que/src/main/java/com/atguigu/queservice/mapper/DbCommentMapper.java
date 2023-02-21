package com.atguigu.queservice.mapper;

import com.atguigu.queservice.entity.DbComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 回复列表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
public interface DbCommentMapper extends BaseMapper<DbComment> {

    int addPraise(String id);
}
