package com.atguigu.queservice.service;

import com.atguigu.queservice.entity.DbQuest;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 问题列表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
public interface DbQuestService extends IService<DbQuest> {
    //增加问题赞的方法
    int addPrase(String id);
}
