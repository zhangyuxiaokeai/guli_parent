package com.atguigu.queservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class DbQuestVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "问题id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "课程id")
    private String courseId;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "问题主题")
    private String title;

    @ApiModelProperty(value = "问题详情")
    private String describes;

    @ApiModelProperty(value = "浏览次数")
    private Integer views;

    @ApiModelProperty(value = "赞")
    private Integer zan;

    @ApiModelProperty(value = "最佳答案用户昵称")
    private String answerNickname;

    @ApiModelProperty(value = "用户头像")
    private String avatarComment;

    @ApiModelProperty(value = "最佳答案")
    private String answer;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

    @ApiModelProperty(value = "问题回答时间")
    @TableField(fill = FieldFill.INSERT)
    private Date answerTime;

    @ApiModelProperty(value = "回答id")
    private String answerId;

    @ApiModelProperty(value = "回答id")
    private Integer commentZan;
}
