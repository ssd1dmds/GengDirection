package com.nailong.gengdirection.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 梗帖返回给前端的 View Object（视图对象）。
 *
 * 和实体 GengPost 的区别：
 * 1. 这里可以塞"额外信息"——比如作者昵称 authorNickname、标签列表 tags 等
 *    （这些字段在 geng_post 表里是没有的，需要 JOIN 查出来）。
 * 2. 可以隐藏不该返回给前端的字段（比如 status 内部用的数字想转成 "已发布" 字符串）。
 *
 * 简单起见，这里只演示加一个 authorNickname。
 */
@Data
public class PostVO {

    private Long id;
    private String title;
    private String content;
    private String sourceUrl;

    private Long authorId;
    /** 作者昵称（来自 user_info.nickname，需要 JOIN 查询） */
    private String authorNickname;

    private Integer heatScore;
    private Integer status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
