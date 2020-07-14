#版本发布
## 2020-07-03：TDF V2.3.1 更新内容
### tdf-base-platform
#### 后端
1. springboot版本升级至2.3.1
1. roleDTO属性精简
1. 配置文件优化
1. bindOAuth2User等接口实现优化
1. tdf common优化
1. 删除无用的saveUserAvatar,增加用户、角色、部门复合分页查询接口
1. 修复composite接口查询重复的bug
1. controller接口调整，解决命名冲突问题
1. final导致无法注入的问题，取消final修饰符
1. 关联表类操作增加同时批量删除和新增接口、分别批量新增和删除接口
1. 认证失败统一异常返回值修改，普通登录的token超过refresh时间，直接返回401
1. 不传递分页参数时候，应默认按照创建时间排序的bug修复
1. 增加根据用户id集合查询用户与角色关联关系方法
1. 增加用户和部门打勾方法、根据roleId和deptId查询用户列表通用方法
1. deptinfo增加parentName属性
1. 统一将isEmpty改为isBlank，修复传输空格代码来的混淆和不管
1. 新增抽取BaseTreeController
1. 内部文档更新

#### 前端
1. 前端增加excel导入导出功能
1. 前端增加全局主题
1. 前端优化路径，优化拦截器和路由
### tdf-sample

#### 后端
1. 去掉不必要的接口
1. 示例

## 2020-06-11：TDF V2.2.7 更新内容
### tdf-base-platform
#### 不分离版本代码
1. tdf-union：基于thymeleaf的不分离版本重构。与分离版本共用数据接口。

#### 前端
1. tdf-ui-vue的form登录后token过期后，增加token过期提示，并重定向到登录页面
1. 数据字典类型与数据字典关联业务

#### 后端
1. 数据字典类型与数据字典关联业务
1. 框架抽象出beforeRemove方法，使得多种remove操作的校验逻辑一致，提高稳定性
1. 初始化sql默认只维护mysql版本，并提供转化为其他sql数据库如oracle、金仓、PG等初始化sql转化方法
1. 树型接口parentId支持空字符串
1. 文件操作优化
1. 用户头像优化
1. 日志切面逻辑优化

#### 其他
1. 前后端方法和类的命名优化
1. 接口命名优化，save改为create，batchSave改为batchCreate，更好地区分新增和更新的概念，减少混淆，降低学习成本
1. 安全性更新；用户信息不返回加密后的密码

### tdf-sample
#### 前端
1. tdf-ui-vue-sample前端示例移植到tdf-sample中，提高开发效率

#### 后端
1. security配置优化
1. mybatis版本的多表关联动态分页查询示例优化
1. jpa版本多表关联代码优化

## 2020-05-28：TDF V2.1.14 更新内容/升级部署说明
### tdf-base-platform
1. 抽象出BaseTreeXX系列，简化开发树状结构业务逻辑工作量
1. tdf-common的BaseXXX抽象更加合理
1. 一些bug修复
1. 优化统一异常逻辑
1. 丰富默认的BaseJoinXXX系列方法
1. OAuth2过滤器逻辑优化
1. swagger、菜单排序等等其他优化项目
#### tdf-sample
1. 增加异常拦截代码示例
1. 代码优化
#### tdf-ui-vue-sample
1. 代码优化
1. 接口调整
1. 丰富学生Demo示例

## 2020-05-14：TDF V2.1.13 更新内容/升级部署说明
- 全新升级,工程结构调整，优化了代码规范
- 工程地址变更为：http://gitlab.taiji.com.cn/IRI/TDF-Base
- 支持多数据库：mysql、kingbase、oracle、postgresql
- 支持jpa和mybatis
- jwt token的加密
- 支持国产化环境，经测试
> 注意：新项目建议采用TDF Base最新版本，旧项目如要升级到此版本，请在创新院产品研发人员支持下进行。

## 2020-02-14：TDF V2.1.12 更新内容/升级部署说明
- springboot版本升级至2.1.12
- 前台tdf-ui-vue工程优化
      -- 优化太极统一认证token refresh功能
      -- 优化菜单编辑，新增功能，增加提示
      -- 优化部门页面显示逻辑
      -- 增加日志根据时间查询功能
- 后台功能优化
      -- 优化后台功能，增加接口中判空
      -- 优化按钮权限查询接口，增加redis缓存
      -- 修复菜单角色级联错误
      
## 2019-12-16：TDF V2.2.1 更新内容/升级部署说明
- springboot版本升级至2.2.1
- 修改后台过时代码（分页中sort方法等）
- 修复统一认证登录中的token刷新功能
- 精简代码（去除注释和冗余代码）

## 2019-12-16：TDF V2.1.11 更新内容/升级部署说明
- springboot版本升级至2.1.11
- 修复统一认证登录中的token刷新功能
- 精简代码（去除注释和冗余代码）

## 2019-11-19：TDF V2.1.9 更新内容/升级部署说明
- springboot版本升级至2.1.9
- 修改分页中page和pagesize为integer类型，以和前台数据类型匹配主要修改tdf-sys中实体。
- 增加登出方法，主要修改tdf-sample-jwt。
- 保存用户方法优化，增加相同loginname存入数据库的限制，主要修改tdf-sys-web。
- 增加数据库user_info表的唯一索引，主要修改tdf-sys中实体。
- 修改未分离版本中页面样式，主要修改tdf-sys-web。
- 增加未分离版本中登陆过程中验证码校验，主要修改tdf-security和tdf-sample，tdf-sys。

## 2019-10-16：TDF V2.1.8 更新内容/升级部署说明
- springboot版本升级至2.1.8
- 如原有后台项目为2.1.6版本，且通过jar形式引用tdf-sys和tdf-sys-web等工程，只需将tdf-sample-jwt项目中pom更新为2.1.8即可。
- 前端工程增加模块开发模式，系统管理部分代码通过npm形式引入，git地址为：ssh://git@gitlab.taiji.com.cn:10022/IRI/tdf/tdf-ui-vue-sample.git
- 此次前端项目新增模块开发模式，如原有项目远程git地址为ssh://git@gitlab.taiji.com.cn:10022/IRI/tdf/TDF-ui-vue.git，
 如已进行开发，因为代码目录发生变更，则不可直接pull代码，需根据实际情况商议后更新代码。如只是下载项目，但暂未进行实际开发，
 则可以直接pull远端代码，或建议前端改用ssh://git@gitlab.taiji.com.cn:10022/IRI/tdf/tdf-ui-vue-sample.git重新下载代码，
 该工程中将原有系统管理部分代码抽离，通过npm形式进行引入，目的是通过该方式进行代码解耦，方便后续代码更新，避免pull代码引起冲突。
工程进行后续开发，该项目通过npm install --save tdf-ui-vue-tdfsys形式引入系统管理部分代码。后续代码更新优化后，只需更新版本号即可。
- 前端工程通过vue-cli3搭建，配置方式与vue-cli2相比较发生改变。
        -- babel，代理放在vue.config.js中进行配置。
        -- 原有cli2中alias-@映射/src放在vue.config.js中进行配置。
- 优化对oracle数据库(需修改配置文件)和金仓数据库(需修改配置文件)的支持。
- 修复bug，包括：菜单路由数据库中path数据错误导致前端页面无法刷新等
- 优化功能

## 2019-08-08：TDF V2.1.6 更新内容
- 升级2.1.6
- 修改菜单路由router从后台获取，相应前台也进行修改。
- 修改部分bug

## 2019-05-29：TDF V2.1.5 更新内容
- 升级2.1.5
- 增加数据查询部分的缓存功能，user，role，dept等，查询增加缓存，部署需要增加redis环境，redis配置在application.properties中
- 修改部分bug

## 2019-04-18：TDF V2.1.4 更新内容
- 升级2.1.4
- 修改前台菜单获取接口，实现前后台分离后菜单管理部分菜单检索由后台提供数据
- 增加参数管理功能（增删改查）
- 修改部分bug

## 2018-12-29：TDF V2.1.1 更新内容
- 升级2.1.1
- 实现前后台分离，前端工程为TDF-ui-vue，后台提供前台接口，前端工程进行展示
- 后台接口由TDF-sample中接口转变而来，原来为controller接口，现更改为RestController接口
- 前台登录验证方式由JWT完成，前台登录请求，后台返回token给前台，以后每次请求，前台需带token进行请求验证
- 增加字典分类
- 初始化sql中增加字典分类数据，使用之前需要初始化sql(菜单数据稍有变化，菜单路径更改)


## 2018-12-06 发布V1.5.18 版本。
## 2018-10-23 发布v1.5.17 版本。
## 2018-05-23 发布V1.5.13 版本。
## 2018-05-09 发布V1.5.12 版本。

