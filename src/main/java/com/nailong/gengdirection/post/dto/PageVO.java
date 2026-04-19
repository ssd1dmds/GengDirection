package com.nailong.gengdirection.post.dto;

import lombok.Data;

import java.util.List;

/**
 * 通用分页返回结构。任何分页接口都可以复用：PageVO&lt;PostVO&gt;、PageVO&lt;UserVO&gt;...
 *
 * 字段含义：
 *  - pageNum   : 当前是第几页（从 1 开始）
 *  - pageSize  : 每页几条
 *  - total     : 满足条件的总条数（不分页时的数量）
 *  - records   : 当前这一页的数据列表
 *
 * 之所以放在 post.dto 包下只是为了让你先跑通；
 * 后面如果其他模块也用到，建议把 PageVO 移到 common 包里共享。
 */
@Data
public class PageVO<T> {

    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<T> records;

    public static <T> PageVO<T> of(Integer pageNum, Integer pageSize, Long total, List<T> records) {
        PageVO<T> vo = new PageVO<>();
        vo.setPageNum(pageNum);
        vo.setPageSize(pageSize);
        vo.setTotal(total);
        vo.setRecords(records);
        return vo;
    }
}
