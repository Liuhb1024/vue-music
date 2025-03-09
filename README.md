# 音乐网站

## 项目概述

飞享音乐网站是一个基于Vue 2和SpringBoot 2开发的前后端分离音乐平台，提供音乐播放、歌手检索、歌单管理等功能，同时包含完整的后台管理系统。

## 项目架构

项目采用前后端分离架构，由三个主要部分组成：

1. **前台客户端** (vue-music-client)：面向用户的音乐网站界面
2. **后台管理系统** (vue-music-manage)：用于管理网站内容的管理员界面
3. **后端服务** (vue-music-project)：提供API接口的SpringBoot应用

## 技术栈

### 前端技术栈

- **核心框架**：Vue 2
- **路由管理**：Vue Router
- **状态管理**：Vuex
- **UI组件库**：Element UI
- **HTTP客户端**：Axios
- **CSS预处理器**：Sass
- **其他**：JavaScript、Font Awesome

### 后端技术栈

- **核心框架**：Spring Boot 2
- **ORM框架**：MyBatis
- **数据库**：MySQL
- **项目管理**：Maven

## 功能特点

### 用户端功能

- **音乐播放**：支持播放/暂停、前/后切换、循环播放、音量控制、播放进度选择
- **歌手浏览**：支持按不同分类条件检索歌手
- **歌单管理**：用户可创建、收藏歌单
- **音乐检索**：支持按不同条件检索音乐
- **个人中心**：管理个人收藏的歌单和音乐

### 管理端功能

- **用户管理**：管理所有用户信息
- **歌手管理**：添加、编辑、删除歌手信息
- **歌曲管理**：上传、编辑、删除音乐
- **歌单管理**：创建、编辑、删除歌单
- **数据统计**：查看网站数据统计信息

## 项目结构

### 整体结构

```
飞享音乐网站
├── vue-music-client/    # 前台客户端
├── vue-music-manage/    # 后台管理系统
├── vue-music-project/   # 后端服务
└── vue2_music.sql       # 数据库脚本
```

### 前台客户端 (vue-music-client)

```
vue-music-client
├── public/              # 静态资源
└── src/
    ├── api/             # 前端数据请求接口
    ├── assets/          # 静态资源目录
    │   ├── img/         # 图片资源
    │   ├── js/          # JavaScript文件
    │   │   └── data/    # 静态数据
    │   └── scss/        # SCSS样式文件
    ├── components/      # Vue组件
    │   ├── common/      # 公共组件
    │   └── search/      # 搜索组件
    ├── mixins/          # 混入
    ├── pages/           # 路由组件
    ├── router/          # 路由
    ├── store/           # 状态管理
    ├── utils/           # 工具类
    ├── App.vue          # 根组件
    └── main.js          # 入口文件
```

### 后台管理系统 (vue-music-manage)

```
vue-music-manage
├── public/              # 静态资源
└── src/
    ├── api/             # 前端数据请求接口
    │   ├── affiliation/ # 歌单-歌手关联
    │   ├── consumer/    # 用户
    │   ├── infopage/    # 首页
    │   ├── singer/      # 歌手
    │   ├── song/        # 歌曲
    │   └── song-list/   # 歌单
    ├── assets/          # 静态资源目录
    │   ├── css/         # CSS样式文件
    │   ├── img/         # 图片资源
    │   └── js/          # JavaScript文件
    ├── components/      # Vue组件
    │   └── common/      # 公共组件
    ├── mixins/          # 混入
    ├── pages/           # 路由组件
    ├── plugins/         # 插件
    ├── router/          # 路由
    ├── store/           # 状态管理
    ├── utils/           # 工具类
    ├── App.vue          # 根组件
    └── main.js          # 入口文件
```

### 后端服务 (vue-music-project)

```
vue-music-project
├── img/                 # 上传的图片
│   ├── consumer-img/    # 用户图片
│   ├── singer-img/      # 歌手图片
│   ├── song-img/        # 歌曲封面
│   └── song-list-img/   # 歌单封面
├── song/                # 上传的歌曲
└── src/
    └── main/
        ├── java/
        │   └── top/
        │       └── zetiny/
        │           └── vuemusicproject/
        │               ├── bean/        # 实体类
        │               ├── config/      # 配置类
        │               ├── controller/  # 控制器
        │               ├── dao/         # 数据访问层
        │               ├── service/     # 服务层
        │               │   └── impl/    # 服务实现类
        │               └── utils/       # 工具类
        └── resources/
            ├── mapper/                  # MyBatis映射文件
            ├── static/                  # 静态资源
            └── templates/               # 模板文件
```

## 核心模块说明

### 1. 播放器模块

封装了播放器组件及播放条组件，实现了音乐播放的核心功能：
- 播放/暂停控制
- 前/后切换
- 循环播放
- 歌曲封面显示
- 音量滑动控制
- 播放进度选择
- 播放列表管理
- 收藏到歌单

### 2. 用户模块

- 用户注册/登录
- 个人信息管理
- 我的音乐管理
- 我的歌单管理

### 3. 歌手模块

- 歌手分类浏览
- 歌手详情查看
- 歌手歌曲列表

### 4. 歌单模块

- 歌单浏览
- 歌单详情
- 歌单收藏
- 创建个人歌单

### 5. 后台管理模块

- 用户管理
- 歌手管理
- 歌曲管理
- 歌单管理

## 项目启动步骤

### 1. 环境准备

#### 必需环境
- Node.js 16+ (推荐使用 v16.20.0)
- Java JDK 8+ (推荐使用 JDK 8)
- Maven 3.6+
- MySQL 5.7+

#### 开发工具
- IDE：推荐使用 IntelliJ IDEA 或 VS Code
- 数据库工具：推荐使用 Navicat 或 MySQL Workbench
- Git：用于版本控制

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE vue2_music DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
```

2. 导入数据：
```bash
# 使用命令行导入
mysql -u username -p vue2_music < vue2_music.sql

# 或使用图形化工具（如Navicat）导入vue2_music.sql文件
```

### 3. 后端服务启动

1. 配置数据库连接：
   - 打开 `vue-music-project/src/main/resources/application.yml`
   - 修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/vue2_music?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf8&useSSL=false
    username: your_username
    password: your_password
```

2. 启动Spring Boot应用：
```bash
# 进入后端项目目录
cd vue-music-project

# 使用Maven打包
mvn clean package

# 运行应用（开发环境）
mvn spring-boot:run

# 或运行打包后的jar（生产环境）
java -jar target/vue-music-project-0.0.1-SNAPSHOT.jar
```

### 4. 前台客户端启动

1. 安装依赖：
```bash
# 进入前台客户端目录
cd vue-music-client

# 安装依赖
npm install
```

2. 配置后端接口地址：
   - 打开 `vue-music-client/src/api/config.js`
   - 修改 API 基础URL（如果需要）：
```javascript
export const BASE_URL = 'http://localhost:8080';
```

3. 启动开发服务器：
```bash
# 开发环境
npm run serve

# 生产环境构建
npm run build
```

### 5. 后台管理系统启动

1. 安装依赖：
```bash
# 进入后台管理系统目录
cd vue-music-manage

# 安装依赖
npm install
```

2. 配置后端接口地址：
   - 打开 `vue-music-manage/src/api/config.js`
   - 修改 API 基础URL（如果需要）：
```javascript
export const BASE_URL = 'http://localhost:8080';
```

3. 启动开发服务器：
```bash
# 开发环境
npm run serve

# 生产环境构建
npm run build
```

### 6. 访问项目

- 前台客户端：http://localhost:8081
- 后台管理系统：http://localhost:8082
- 后端API服务：http://localhost:8080

### 7. 常见问题处理

1. 端口占用问题：
   - 前端项目可在 `vue.config.js` 中修改端口
   - 后端项目可在 `application.yml` 中修改端口

2. 跨域问题：
   - 后端已配置跨域支持
   - 如需修改，请查看 `vue-music-project/src/main/java/top/zetiny/vuemusicproject/config/WebMvcConfig.java`

3. 文件上传问题：
   - 确保 `vue-music-project/img` 和 `vue-music-project/song` 目录存在且有写入权限
   - 可在 `application.yml` 中修改文件上传配置

## 项目展示

### 网站前台

#### 登录

![image-20250309174252291](F:\project\feixiang-website\README.assets\image-20250309174252291.png)

#### 注册

![image-20250309174244749](F:\project\feixiang-website\README.assets\image-20250309174244749.png)

#### 个人设置

![image-20250309174308169](F:\project\feixiang-website\README.assets\image-20250309174308169.png)

#### 首页

![image-20250309173746453](F:\project\feixiang-website\README.assets\image-20250309173746453.png)

#### 检索所有歌手

![image-20250309173825974](F:\project\feixiang-website\README.assets\image-20250309173825974.png)

#### 检索所有歌单
![image-20250309173837402](F:\project\feixiang-website\README.assets\image-20250309173837402.png)

#### 歌单详情页
![image-20250309173849721](F:\project\feixiang-website\README.assets\image-20250309173849721.png)

#### 未登录-我的音乐
![image-20250309173903722](F:\project\feixiang-website\README.assets\image-20250309173903722.png)

#### 已登录-我的音乐
![image-20250309173922802](F:\project\feixiang-website\README.assets\image-20250309173922802.png)

#### 播放列表
![image-20250309173933294](F:\project\feixiang-website\README.assets\image-20250309173933294.png)

### 网站后台

#### 后台登录
![image-20250309174016640](F:\project\feixiang-website\README.assets\image-20250309174016640.png)

#### 系统主页

![image-20250309174114959](F:\project\feixiang-website\README.assets\image-20250309174114959.png)

#### 用户管理

![image-20250309174100273](F:\project\feixiang-website\README.assets\image-20250309174100273.png)

#### 歌手管理
![image-20250309174127672](F:\project\feixiang-website\README.assets\image-20250309174127672.png)

#### 歌手-歌曲管理
![image-20250309174145524](F:\project\feixiang-website\README.assets\image-20250309174145524.png)

#### 歌单管理
![image-20250309174157185](F:\project\feixiang-website\README.assets\image-20250309174157185.png)

#### 歌单-歌曲管理
![image-20250309174207177](F:\project\feixiang-website\README.assets\image-20250309174207177.png)

## 环境依赖

### 前端依赖

```
├── @babel/core@7.20.7
├── @babel/eslint-parser@7.19.1
├── @vue/cli-plugin-babel@5.0.8
├── @vue/cli-plugin-eslint@5.0.8
├── @vue/cli-service@5.0.8
├── axios@1.2.2
├── core-js@3.27.1
├── element-ui@2.15.12
├── eslint-plugin-vue@8.7.1
├── eslint@7.32.0
├── font-awesome@4.7.0
├── node-sass@8.0.0
├── sass-loader@13.2.0
├── vue-router@3.6.5
├── vue-template-compiler@2.7.14
├── vue@2.7.14
└── vuex@3.6.2
```

### 后端依赖

详见项目中的`pom.xml`文件
