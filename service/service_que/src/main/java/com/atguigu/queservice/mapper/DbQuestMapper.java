package com.atguigu.queservice.mapper;

import com.atguigu.queservice.entity.DbQuest;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 问题列表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-11-30
 */
public interface DbQuestMapper extends BaseMapper<DbQuest> {

    int addpraseMethod(String id);
}
