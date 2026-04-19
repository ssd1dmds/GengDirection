package com.nailong.gengdirection.post.controller;

import com.nailong.gengdirection.common.Result;
import com.nailong.gengdirection.post.entity.GengPost;
import com.nailong.gengdirection.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 梗帖 REST 接口。
 *
 * 注解说明：
 *   - @RestController            = @Controller + @ResponseBody，方法返回值自动转 JSON
 *   - @RequestMapping("/posts")  本类下所有接口的统一前缀
 *
 * 所有接口都用 Result<T> 包一层，配合全局异常处理统一返回格式。
 *
 * 接口约定（前后端联调用，先和前端同学对齐这个表）：
 *   GET    /posts/{id}                 按 id 查           ← 已实现
 *   GET    /posts?pageNum=1&pageSize=10  分页查已发布      ← TODO
 *   POST   /posts                      新增（body=DTO）   ← TODO
 *   DELETE /posts/{id}                 删除               ← TODO
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /** GET /posts/{id} —— 参考样例 */
    @GetMapping("/{id}")
    public Result<GengPost> getById(@PathVariable Long id) {
        return Result.success(postService.getById(id));
    }

    // TODO: GET /posts?pageNum=1&pageSize=10
    //   @GetMapping
    //   public Result<PageVO<PostVO>> page(
    //           @RequestParam(defaultValue = "1") Integer pageNum,
    //           @RequestParam(defaultValue = "10") Integer pageSize) { ... }

    // TODO: POST /posts
    //   @PostMapping
    //   public Result<Long> create(@RequestBody PostCreateDTO dto) { ... }

    // TODO: DELETE /posts/{id}
    //   @DeleteMapping("/{id}")
    //   public Result<Void> delete(@PathVariable Long id) { ... }
}
