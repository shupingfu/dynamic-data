package com.example.dynamic.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Article {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String title;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
