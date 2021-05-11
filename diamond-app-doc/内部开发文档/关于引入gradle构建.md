
随着技术发展，公司内部有项目希望使用gradle构建。
在当前gradle和maven构建需求并存的情况下，TDF选择同时引入对gradle和maven的支持。
用户初始化构建工程时候，需要maven和gradle任选其一。

## 关于gradle构建
* [为什么 Spring Boot 2.3.0 放弃 Maven 转投 Gradle ？](http://blog.didispace.com/spring-boot-gradle/)

### 一些最佳实践建议
1. [避免在脚本中使用命令式逻辑](https://docs.gradle.org/current/userguide/authoring_maintainable_build_scripts.html#sec:avoid_imperative_logic_in_scripts)


### 关于插件
* [Gradle核心(四)：Gradle插件](https://segmentfault.com/a/1190000021096551)


### 关于共用maven仓库

### 关于统一管理版本号
http://codepub.cn/2017/05/09/Gradle-uses-extended-attributes-to-manage-dependent-version-numbers/

### 统一使用gradle.properties文件声明变量
如果是多模块项目，在根项目中定义的属性可以在子项目中获取。
spring源码是否使用了gradle.properties

### 关于该 module 的本地源码代替jar
https://juejin.cn/post/6844904088446959623  的5.3