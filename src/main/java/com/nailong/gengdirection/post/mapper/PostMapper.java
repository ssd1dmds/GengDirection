package com.nailong.gengdirection.post.mapper;

import com.nailong.gengdirection.post.entity.GengPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 梗帖 Mapper（DAO 层）。
 *
 * 只声明方法签名，真正的 SQL 写在 resources/mapper/PostMapper.xml 里，
 * XML 通过 namespace + id 与本接口的方法一一对应。
 *
 * 已经写好了一个 selectById 作为参考样例（接口 + XML + Service + Controller 一条链都通了）。
 * 其它方法照着 selectById 的写法补上。
 */
@Mapper
public interface PostMapper {

    /** 按主键查询单条梗帖 —— 参考样例 */
    GengPost selectById(@Param("id") Long id);

    // TODO: int insert(GengPost post);
    //   提示：XML 里加 useGeneratedKeys="true" keyProperty="id"，自增主键会回填到 entity.id

    // TODO: int updateById(GengPost post);
    //   提示：XML 用 <set> + <if> 做"非空字段才更新"

    // TODO: int deleteById(@Param("id") Long id);

    // TODO: List<PostVO> selectPublishedPage(@Param("offset") int offset,
    //                                       @Param("pageSize") int pageSize);
    //   提示：JOIN user_info 拿作者昵称 nickname，直接 resultType 到 PostVO

    // TODO: long countPublished();
    //   提示：配合分页查询统计 status=2 的总数
}
