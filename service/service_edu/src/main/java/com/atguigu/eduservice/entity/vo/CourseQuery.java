package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "名称查询，模糊查询")
    private String title;
    @ApiModelProperty(value = "发布状态查询")
    private String status;
}
