# News 新闻头条

一个基于 Spring Boot + MyBatis + MySQL 的新闻头条练手项目，提供头条的分页浏览、热点排行、详情查看，以及用户注册、登录和头条的发布、修改、删除。

## 技术栈

| 组件 | 版本 / 说明 |
|---|---|
| Spring Boot | 3.1.3 |
| MyBatis | mybatis-spring-boot-starter 3.0.0（全注解 SQL，无 XML 映射） |
| MySQL | mysql-connector-j |
| Druid | 1.2.23（连接池，依赖已引入） |
| JJWT | 0.12.3（`JwtHelper` 工具类，当前未启用） |
| Servlet | Jakarta Servlet，控制器基于 `@WebServlet` |

## 目录结构

```
src/main/java/com/chx
├── NewsApplication.java          # 启动类：@SpringBootApplication + @MapperScan + @ServletComponentScan
├── common/Result.java            # 统一响应体 {code, msg, data}
├── controller/
│   ├── UserController.java       # /user/*     登录、注册、退出
│   └── HeadlineController.java   # /headline/* 列表、热点、详情、增删改
├── service/                      # 业务接口
│   └── impl/                     # 业务实现（构造器注入 Mapper）
├── mapper/                       # 三个注解式 Mapper 接口
│   ├── NewsUserMapper.java
│   ├── NewsTypeMapper.java
│   └── NewsHeadlineMapper.java
├── entity/                       # NewsUser / NewsType / NewsHeadline
├── pojo/vo/                      # HeadlineVO（列表）/ HeadlineDetailVO（详情）
└── utils/                        # WebUtil、MD5Util、JwtHelper，及 JavaWeb 阶段遗留工具
src/main/resources/application.yml
```

> 注意：控制器是传统 `HttpServlet`（`@WebServlet`），并非 Spring MVC 的 `@RestController`，两者通过 `@ServletComponentScan` 注册；字段注入仍由 Spring 完成。

## 数据库准备

默认连接 `localhost:3306/toutiao`。三张表按代码中的 SQL 反推，参考 DDL 如下（请以实际库为准）：

```sql
CREATE DATABASE IF NOT EXISTS toutiao DEFAULT CHARSET utf8mb4;

CREATE TABLE news_user (
  uid      INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50)  NOT NULL UNIQUE,
  user_pwd VARCHAR(64)  NOT NULL,          -- 存储 MD5 值，非明文
  nick_name VARCHAR(50)
);

CREATE TABLE news_type (
  tid       INT PRIMARY KEY AUTO_INCREMENT,
  type_name VARCHAR(50) NOT NULL
);

CREATE TABLE news_headline (
  hid         INT PRIMARY KEY AUTO_INCREMENT,
  title       VARCHAR(200) NOT NULL,
  article     TEXT,
  type        INT,                          -- 关联 news_type.tid
  publisher   INT,                          -- 关联 news_user.uid
  page_views  INT DEFAULT 0,
  create_time DATETIME,
  update_time DATETIME,
  is_deleted  TINYINT DEFAULT 0             -- 逻辑删除标记
);
```

## 配置

`src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/toutiao?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
    username: root
    password: 123

mybatis:
  configuration:
    map-underscore-to-camel-case: true
  mapper-locations: classpath*:mapper/*.xml
```

请按本机情况修改 `username` / `password`。

## 接口说明

响应统一为 `{"code":1,"msg":"success","data":...}`（`code` 1 成功 / 0 失败）。

### 头条（`/headline/*`）

| 方法 | 路径 | 参数 | 说明 |
|---|---|---|---|
| GET | `/headline/list` | `type`(可选)、`page`(默认1)、`pageSize`(默认5) | 按类型分页，返回 `{list, total}` |
| GET | `/headline/hot` | - | 按浏览量取前 10 条热点 |
| GET | `/headline/detail` | `hid` | 详情，读取时浏览量 +1 |
| GET | `/headline/types` | - | 全部分类 |
| POST | `/headline/add` | `title`、`article`、`type` | 发布，**需登录** |
| POST | `/headline/update` | `hid`、`title`、`article`、`type` | 修改，**需登录** |
| POST | `/headline/delete` | `hid` | 逻辑删除，**需登录** |

### 用户（`/user/*`）

| 方法 | 路径 | 参数 | 说明 |
|---|---|---|---|
| POST | `/user/login` | `username`、`userPwd` | 登录成功写入 session |
| POST | `/user/register` | `username`、`userPwd`、`nickName` | 注册，密码 MD5 后入库 |
| GET | `/user/logout` | - | 注销 session |

登录态保存在 `HttpSession` 的 `user` 属性中；发布/修改/删除类接口会校验登录，未登录返回 `请先登录`。

## 运行

```bash
# 开发运行
mvn spring-boot:run

# 或打包后运行
mvn clean package -DskipTests
java -jar target/News-1.0-SNAPSHOT.jar
```

默认端口 8080，接口示例：`http://localhost:8080/headline/types`。

## 注意事项

- **JDK 版本**：Spring Boot 3.1.x 官方支持 Java 17–20。使用 JDK 25 编译可通过，但启动时可能因 Spring 6.0 内置 ASM 不支持高版本字节码而失败，建议使用 **JDK 17**。
- `application.yml` 中的 `mapper-locations` 指向 `classpath*:mapper/*.xml`，但项目全部使用注解 SQL，该目录不存在，不影响运行。
- `utils` 下的 `JDBCUtil`、`MyBatisUtil`、`MyDruidDataSourceFactory` 是早期 JavaWeb 阶段的遗留代码，依赖已不存在的 `druid.properties` / `mybatis-config.xml`，当前未被引用（不会被加载），后续可清理。
- `JwtHelper` 已实现但暂未接入业务，登录目前走的是 Session 方案。
- `/headline/update`、`/headline/delete` 目前只校验登录、不校验归属，任意登录用户可操作他人头条，如需上线应补充权限校验。
