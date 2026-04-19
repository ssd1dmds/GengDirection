package com.nailong.gengdirection.post.dto;

import lombok.Data;

/**
 * 创建梗帖的请求体（Data Transfer Object）。
 *
 * 为什么不用 GengPost 实体直接接收？
 * 1. 实体类有数据库自增 id、创建时间等"前端不该传"的字段，
 *    暴露出去会让客户端误以为可以传，造成安全隐患。
 * 2. 入参字段和数据库表字段并不总是一一对应（例如新增时不需要 heatScore）。
 * 3. 后续做参数校验（@NotBlank 之类）只在 DTO 上加，不污染实体。
 */
@Data
public class PostCreateDTO {

    /** 标题，必填，长度 ≤ 120 */
    private String title;

    /** 正文内容，必填 */
    private String content;

    /** 原帖来源 URL，选填 */
    private String sourceUrl;

    /**
     * 作者 ID。
     * 真实项目里这个字段应该从登录态（Session/JWT）里取，
     * 不应该让前端自己传——这里先简化处理，等做完登录模块再改。
     */
    private Long authorId;
}
