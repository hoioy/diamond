该项目主要提供开发中常用的方法、工具类、前后台分离版本的返回DTO、枚举。
### 规范
* 时刻保持diamond-common的精简和小巧，及时删除冗余、错误代码
* diamond-common中的类不应该出现@Component等初始化对象的注解

### 常用小技巧
1. DTO统一继承BaseDTO
1. 所有自定义异常继承BaseException

#### 讨论记录
* 查看[讨论记录](http://gitlab.diamond.com.cn/IRI/diamond/Diamond-common/wikis/%E8%AE%A8%E8%AE%BA%E8%AE%B0%E5%BD%95list)
