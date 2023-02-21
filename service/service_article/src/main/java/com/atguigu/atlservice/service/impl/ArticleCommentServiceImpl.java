package com.atguigu.atlservice.service.impl;

import com.atguigu.atlservice.client.UcenterClient;
import com.atguigu.atlservice.client.UcenterClient;
import com.atguigu.atlservice.entity.ArticleComment;
import com.atguigu.atlservice.mapper.ArticleCommentMapper;
import com.atguigu.atlservice.service.ArticleCommentService;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-10-30
 */
@Service
public class ArticleCommentServiceImpl extends ServiceImpl<ArticleCommentMapper, ArticleComment> implements ArticleCommentService {
    @Autowired
    private UcenterClient ucenterClient;
    //增加文章评论的方法
    @Override
    public boolean addComment(ArticleComment comment, String memberId) {
        //获取会员信息
        UcenterMemberOrder infoUc = ucenterClient.getInfoUc(memberId);
        //获取会员头像
        String avatar = infoUc.getAvatar();
        //获取会员昵称
        String nickname = infoUc.getNickname();
        comment.setAvatar(avatar);
        comment.setMemberId(memberId);
        comment.setNickname(nickname);
        int insert = baseMapper.insert(comment);
        if (insert==0){
            throw new GuliException(20001,"添加失败");
        }
        return true;
    }
}
