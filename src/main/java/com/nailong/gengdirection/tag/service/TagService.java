package com.nailong.gengdirection.tag.service;

import com.nailong.gengdirection.tag.entity.TagInfo;
import java.util.List;

public interface TagService {
    List<TagInfo> listAllTags();

    void addTag(String tagName);

    void addTagsToPost(Long postId, List<Long> tagIds);

    void removeTagFromPost(Long postId, Long tagId);

    List<Long> listPostsByTagId(Long tagId, int pageNum, int pageSize);
}
