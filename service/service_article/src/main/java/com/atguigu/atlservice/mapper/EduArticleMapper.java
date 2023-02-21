package com.atguigu.atlservice.mapper;

import com.atguigu.atlservice.entity.EduArticle;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-10-26
 */
public interface EduArticleMapper extends BaseMapper<EduArticle> {

    void updatePraiseById(@PathVariable("id") String id);
}
