package com.atguigu.queservice.service.impl;

import com.atguigu.queservice.entity.DbComment;
import com.atguigu.queservice.mapper.DbCommentMapper;
import com.atguigu.queservice.service.DbCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 回复列表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@Service
public class DbCommentServiceImpl extends ServiceImpl<DbCommentMapper, DbComment> implements DbCommentService {

    @Override
    public int addPraiseComment(String answerId) {
        String id=answerId;
        int status=baseMapper.addPraise(id);
        return status;
    }
}
