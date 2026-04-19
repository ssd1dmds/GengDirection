# GengDirection 协作分工

> 4 人协作开发指南。每个模块按照 `post` 包的目录结构镜像，互不阻塞。

---

## 一、统一约定（所有人都要遵守）

### 1. 目录结构（每个模块都按 `post` 包来）

```
<模块名>/
├─ controller/   REST 接口
├─ service/      业务接口
│   └─ impl/     业务实现
├─ mapper/       MyBatis Mapper 接口（XML 在 resources/mapper/ 下）
├─ entity/       和数据库表一一对应
└─ dto/          请求 / 响应对象（不要直接把 entity 暴露给前端）
```

### 2. 编码规则

- 所有接口统一用 `Result<T>` 包返回
- 业务异常统一抛 `GengException`，全局 `GlobalExceptionHandler` 已处理
- Mapper 接口的方法名必须与 XML 里的 `id` 一致
- 入参 / 出参一律走 DTO / VO，**严禁**把 entity 直接当请求体或响应体
- 命名风格：表 `snake_case`、Java 字段 `camelCase`，
  `application.properties` 已开 `map-underscore-to-camel-case=true`，自动转换

### 3. 协作流程

- 各自开分支：`feature/user`、`feature/post`、`feature/tag`、`feature/interact`
- 合并前先 `git pull --rebase origin main`
- **不要提交** `application-local.properties`（已在 `.gitignore`）
- PR 描述里写清"新增了哪些接口、改动了哪些公共代码"

### 4. 参考样例

`post` 包里 `GET /posts/{id}` 一条链端到端已经写通（Controller → Service → ServiceImpl → Mapper → XML），
其它模块照着这个格式抄就行。

---

## 二、4 人分工

### A 同学 —— 用户认证模块

- **包名**：`com.nailong.gengdirection.user`
- **负责的表**：`user_info` / `role_info` / `user_role`
- **核心实体**：`UserInfo`、`RoleInfo`
- **接口**：

| 方法 | URL | 说明 |
|---|---|---|
| POST | `/users/register` | 注册，body: `RegisterDTO {username, password, nickname}` |
| POST | `/users/login` | 登录，body: `LoginDTO {username, password}` |
| GET  | `/users/{id}` | 查个人信息（返回 `UserVO`，**不带密码**） |

- **要点**：
  - 密码用 `BCryptPasswordEncoder` 加密后存入 `password_hash`
    - 依赖：`org.springframework.security:spring-security-crypto`
  - `data.sql` 里的占位 hash 要换成真实 BCrypt 串
  - 登录态的实现（Session / 简易 token）和 D 同学对一下

- **完成标准**：
  Postman 能注册新用户、登录返回成功、`GET /users/{id}` 查得到。

---

### B 同学 —— 梗帖核心模块

- **包名**：`com.nailong.gengdirection.post` ✅ **脚手架已搭好**
- **负责的表**：`geng_post`
- **核心实体**：`GengPost`
- **接口**：

| 方法 | URL | 状态 |
|---|---|---|
| GET    | `/posts/{id}` | ✅ 已写（参考样例） |
| GET    | `/posts?pageNum=1&pageSize=10` | TODO |
| POST   | `/posts` | TODO，body: `PostCreateDTO` |
| PUT    | `/posts/{id}` | TODO，body: `PostUpdateDTO` |
| DELETE | `/posts/{id}` | TODO |

- **要点**：
  - 已有的 `// TODO` 注释里都写了提示，照着补
  - `authorId` 一开始让前端传，等 A 同学的登录做完后统一改成从登录态取
  - 列表查询要 JOIN `user_info` 拿作者昵称（XML 已给出参考写法）

- **完成标准**：
  4 个接口全部跑通，浏览器访问 `/posts` 能看到 `data.sql` 里的种子帖。

---

### C 同学 —— 标签模块

- **包名**：`com.nailong.gengdirection.tag`
- **负责的表**：`tag_info` / `geng_post_tag`
- **核心实体**：`TagInfo`（关联表 `geng_post_tag` 一般不建实体，XML 里直接 JOIN）
- **接口**：

| 方法 | URL | 说明 |
|---|---|---|
| GET    | `/tags` | 所有标签列表 |
| POST   | `/tags` | 新增标签，body: `TagCreateDTO {tagName}` |
| POST   | `/posts/{postId}/tags` | 给帖子打标签，body: `{tagIds:[1,2,3]}` |
| DELETE | `/posts/{postId}/tags/{tagId}` | 移除某个标签 |
| GET    | `/tags/{id}/posts?pageNum=1&pageSize=10` | 按标签查帖子（**自己 JOIN，不动 B 的代码**） |

- **依赖**：B 同学的 `geng_post` 表
- **要点**：
  - 重复打同一个标签要幂等（`INSERT IGNORE` 或先查再插）
  - 删除帖子时关联的 `geng_post_tag` 已经走 `ON DELETE CASCADE`，C 不用额外清理

- **完成标准**：
  能给种子数据里 "第一次实验梗" 那条帖打 / 移除标签，按标签筛选返回正确结果。

---

### D 同学 —— 互动模块

- **包名**：`com.nailong.gengdirection.interact`（或拆成 `comment` + `favorite` 两个包）
- **负责的表**：`post_comment` / `user_favorite_post`
- **核心实体**：`PostComment`（收藏关联表不建实体）
- **接口**：

**评论：**

| 方法 | URL | 说明 |
|---|---|---|
| POST   | `/posts/{postId}/comments` | 发评论，body: `CommentCreateDTO {content, userId}` |
| GET    | `/posts/{postId}/comments?pageNum=1&pageSize=20` | 评论列表（按时间倒序） |
| DELETE | `/comments/{id}` | 删评论（只能删自己的，登录后接入） |

**收藏：**

| 方法 | URL | 说明 |
|---|---|---|
| POST   | `/posts/{postId}/favorite` | 收藏（幂等，重复点不报错） |
| DELETE | `/posts/{postId}/favorite` | 取消收藏 |
| GET    | `/users/{userId}/favorites` | 我的收藏列表 |

- **依赖**：A 同学的 `user_info`、B 同学的 `geng_post`
- **要点**：
  - 评论 / 收藏不存在的帖子要抛 `GengException(404, "...")`
  - 评论列表要 JOIN `user_info` 拿评论人昵称
  - `userId` 暂时让前端传，A 做完登录后改成从登录态取

- **完成标准**：
  能给种子帖发评论、查列表、收藏、取消收藏。

---

## 三、跨模块的 3 个"接缝"

模块之间只有 3 个真正会撕的地方，提前对齐：

### 1. 登录态 → 全员

A 做完登录前，B / C / D 的需要 `userId` / `authorId` 的接口先用 `@RequestParam Long userId` 让前端传。
A 做完后**统一一次性**替换成从 Session（或 token）取。

### 2. 按标签筛帖子 → B vs C

由 **C 同学** 写 `GET /tags/{id}/posts`，自己 JOIN `geng_post_tag` + `geng_post`，**不修改 B 的代码**。

### 3. 删帖级联 → B vs C/D

`schema.sql` 里 `geng_post_tag`、`post_comment`、`user_favorite_post` 都有 `ON DELETE CASCADE`，
B 删帖时数据库会自动级联删除关联数据，C / D **不需要额外清理**。

---

## 四、推荐开发顺序

并行开发 OK，但**联调和合并**按这个顺序最不容易卡：

1. **B 先合一版**（参考样板，A / C / D 复制粘贴改名字省事）
2. **A 接着合**（C / D 测试时需要 `userId`，A 没合大家只能写假数据）
3. **C 和 D 并行**，最后合
4. **整体联调**：四人一起跑一次端到端流程
   注册 → 登录 → 发帖 → 打标签 → 评论 → 收藏

---

## 五、提交前 Checklist

每个人合并前自查一遍：

- [ ] 接口都用 `Result<T>` 包了
- [ ] 业务异常都抛 `GengException`，没有裸 `throw new RuntimeException`
- [ ] 没有把 entity 当请求体 / 响应体
- [ ] 没有提交 `application-local.properties` 或任何带真实密码的文件
- [ ] 自己的接口在 Postman / curl 里跑通过
- [ ] 跑了一次 `./gradlew.bat test`，没有红
