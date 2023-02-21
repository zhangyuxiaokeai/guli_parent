package com.atguigu.atlservice.entity.vo;


import lombok.Data;

@Data
public class ArticleVo {
    private String id;
    private String textId;
    private String title;
    private String abstractl;
    private Integer praise;
    private String author;
    private Long version;
    private String avater;
    private String content;
}
