package com.nailong.gengdirection.post.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 梗帖实体类（一对一对应数据库表 geng_post）
 *
 * 注意：
 * 1. 字段类型尽量和数据库列类型匹配（DATETIME -> LocalDateTime, TINYINT -> Integer 等）。
 * 2. 数据库列是下划线命名（source_url），Java 字段用驼峰（sourceUrl）。
 *    因为 application.properties 里设置了
 *      mybatis.configuration.map-underscore-to-camel-case=true
 *    所以 MyBatis 会自动把 source_url -> sourceUrl，不需要手写 alias。
 * 3. @Data 是 Lombok 注解，编译期自动生成 getter/setter/toString/equals/hashCode。
 */
@Data
public class GengPost {

    /** 主键 */
    private Long id;

    /** 标题 */
    private String title;

    /** 正文内容 */
    private String content;

    /** 原帖来源 URL，可空 */
    private String sourceUrl;

    /** 发帖人 ID（外键 -> user_info.id） */
    private Long authorId;

    /** 热度分数 */
    private Integer heatScore;

    /** 状态：1=草稿，2=已发布，3=已归档 */
    private Integer status;

    /** 创建时间（数据库默认 CURRENT_TIMESTAMP） */
    private LocalDateTime createdAt;

    /** 最后更新时间（数据库 ON UPDATE CURRENT_TIMESTAMP） */
    private LocalDateTime updatedAt;
}
