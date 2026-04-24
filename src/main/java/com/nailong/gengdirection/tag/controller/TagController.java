package com.nailong.gengdirection.tag.controller;

import com.nailong.gengdirection.tag.entity.TagInfo;
import com.nailong.gengdirection.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor //  必须加，用于构造器注入
public class TagController {

    private final TagService tagService; //  使用 final + 构造器注入，替代 @Autowired

    @GetMapping
    public List<TagInfo> listAllTags() {
        return tagService.listAllTags();
    }

    // 其他接口...
}