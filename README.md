# GengDirection

这个 README 只保留一件事：**项目拉下来后，你要做什么**。

## 拉下来后 5 步走

1. 进入项目目录

```powershell
cd D:\JavaLecture\Lab2\GengDirection
```

2. 准备 MySQL 数据库（先确认 MySQL 已启动）

```powershell
mysql -u root -p < D:\JavaLecture\Lab2\GengDirection\src\main\resources\db\schema.sql
mysql -u root -p < D:\JavaLecture\Lab2\GengDirection\src\main\resources\db\data.sql
```

3. 配置数据库连接

打开 `src/main/resources/application.properties`，至少确认这 3 项：

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`

如果你用环境变量，也可以设置：`DB_URL`、`DB_USERNAME`、`DB_PASSWORD`。

4. 跑测试检查环境是否正常

```powershell
.\gradlew.bat test
```

5. 启动项目

```powershell
.\gradlew.bat bootRun
```

默认访问端口：`8080`

## 第一次协作前再检查一次

- 不要提交真实数据库密码
- 新功能请走分支（例如：`feature/user-crud`）
- 提交前先拉主干再推送
