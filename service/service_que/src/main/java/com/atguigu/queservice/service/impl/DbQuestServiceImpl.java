package com.atguigu.queservice.service.impl;

import com.atguigu.queservice.entity.DbQuest;
import com.atguigu.queservice.mapper.DbQuestMapper;
import com.atguigu.queservice.service.DbQuestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问题列表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
@Service
public class DbQuestServiceImpl extends ServiceImpl<DbQuestMapper, DbQuest> implements DbQuestService {
    //给问题增加赞的方法
    @Override
    public int addPrase(String id) {
       int status=baseMapper.addpraseMethod(id);
        return status;
    }
}
