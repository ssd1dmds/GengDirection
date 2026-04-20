package com.nailong.gengdirection.tag.controller;

import com.nailong.gengdirection.common.Result;
import com.nailong.gengdirection.tag.entity.TagInfo;
import com.nailong.gengdirection.tag.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TagController {

    @Autowired
    private TagService tagService;

    // 获取所有标签
    @GetMapping("/tags")
    public Result<List<TagInfo>> listAllTags() {
        List<TagInfo> list = tagService.listAllTags();
        return Result.success(list);
    }

    // 新增标签
    @PostMapping("/tags")
    public Result<?> addTag(@RequestBody Map<String, String> body) {
        tagService.addTag(body.get("tagName"));
        return Result.success();
    }

    // 给帖子添加标签
    @PostMapping("/posts/{postId}/tags")
    public Result<?> addTagsToPost(@PathVariable Long postId,
                                   @RequestBody Map<String, List<Long>> body) {
        tagService.addTagsToPost(postId, body.get("tagIds"));
        return Result.success();
    }

    // 删除帖子的标签
    @DeleteMapping("/posts/{postId}/tags/{tagId}")
    public Result<?> removeTagFromPost(@PathVariable Long postId,
                                       @PathVariable Long tagId) {
        tagService.removeTagFromPost(postId, tagId);
        return Result.success();
    }

    // 根据标签ID查帖子
    @GetMapping("/tags/{id}/posts")
    public Result<List<Long>> listPostsByTagId(
            @PathVariable Long id,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        List<Long> posts = tagService.listPostsByTagId(id, pageNum, pageSize);
        return Result.success(posts);
    }
}