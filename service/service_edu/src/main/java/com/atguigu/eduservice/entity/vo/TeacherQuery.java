package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeacherQuery {
@ApiModelProperty(value = "教师查询，模糊查询")
    private String name;
    @ApiModelProperty(value = "头衔1高级讲师2首席讲师")
    private Integer level;
    @ApiModelProperty(value = "查询开始时间",example = "2019-01-01 10:10:10")
    private String begin;
    @ApiModelProperty(value = "查询开始时间",example = "2019-12-01 10:10:10")
    private String end;


}
