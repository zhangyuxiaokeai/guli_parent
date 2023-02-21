package com.atguigu.educenter.mapper;

import com.atguigu.educenter.entity.UcenterMember;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-09-27
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {
    //查询一天的登录人数
    Integer countRegisterDay(@Param("day") String day);
}
