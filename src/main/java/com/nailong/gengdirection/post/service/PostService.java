package com.nailong.gengdirection.post.service;

import com.nailong.gengdirection.post.entity.GengPost;

/**
 * 梗帖业务接口。
 *
 * 接口只列方法签名 + 中文说明，具体逻辑在 PostServiceImpl。
 * 这样 Controller 只依赖接口，方便后续替换实现 / 写单测。
 *
 * 已写好 getById 作为参考样例；其它方法照样子补。
 */
public interface PostService {

    /** 按 id 查单条；找不到时抛 GengException —— 参考样例 */
    GengPost getById(Long id);

    // TODO: Long create(PostCreateDTO dto);
    //   返回新生成的主键 id；记得校验入参非空

    // TODO: PageVO<PostVO> pagePublished(Integer pageNum, Integer pageSize);
    //   入参兜底：pageNum<1 -> 1，pageSize 限制在 [1,100]

    // TODO: void deleteById(Long id);
}
