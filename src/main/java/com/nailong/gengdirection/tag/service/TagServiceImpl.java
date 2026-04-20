package com.nailong.gengdirection.tag.service.impl;

import com.nailong.gengdirection.tag.entity.TagInfo;
import com.nailong.gengdirection.tag.mapper.TagMapper;
import com.nailong.gengdirection.tag.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private final TagMapper tagMapper;

    // 构造注入
    public TagServiceImpl(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    @Override
    public List<TagInfo> listAllTags() {
        return tagMapper.listAllTags();
    }

    @Override
    public void addTag(String tagName) {
        TagInfo tag = new TagInfo();
        tag.setTagName(tagName);
        tagMapper.insertTag(tag);
    }

    @Override
    @Transactional
    public void addTagsToPost(Long postId, List<Long> tagIds) {
        for (Long tagId : tagIds) {
            tagMapper.insertPostTag(postId, tagId);
        }
    }

    @Override
    public void removeTagFromPost(Long postId, Long tagId) {
        tagMapper.deletePostTag(postId, tagId);
    }

    @Override
    public List<Long> listPostsByTagId(Long tagId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return tagMapper.listPostIdsByTagId(tagId, offset, pageSize);
    }
}