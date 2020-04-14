# Sys 模块介绍

## 简述功能

       该子项目主要给TDF_sys_web、TDF_security等项目提供数据模型、数据访问服务，是整个系统的基础项目。

## 包路径规划

    cn
      +-com
        +-taiji
          +-sys
             |
             +-domain         实体（Entity）与数据访问层（Repository）置于此包
             |
             +-dto            数据传输对象(Data Transfer Object)
             |
             +-exception      统一异常处理(Exception)
             |
             +-json           JSON异常处理配置项
             |
             +-properties     自定义属性（Property）
             |
             +-service        逻辑层（Service）

                

## 相关数据库结构

![系统表关系图](http://gitlab.taiji.com.cn/IRI/TDF_sys/raw/master/images/sys-db.png)

## 常用小技巧

### 初始化数据

#### 指定数据库类型SQL文件初始化
    spring.datasource.schema[0]=classpath:/sys_db.sql
    spring.datasource.initialize = true               >>初始化成功后，赋值为false
    spring.datasource.continue-on-error=true

#### 针对不同数据库，测试用例初始化

###### 1、初始化用户、角色及相关联
              
     修改 目标配置文件（application.properties）数据库地址、名称、用户和密码为当前自己工程的相关数据项;
       
     执行 UserRoleTest.java里面的带有注解@Test的方法
                      
###### 2、初始化菜单和角色相关联      

     执行MenuTest.java里面带有注解@Test的方法
                        
### mysql 的配置

    spring.datasource.url=jdbc:mysql://127.0.0.1:3306/db_name?useUnicode=true&characterEncoding=utf-8
    spring.datasource.username=***
    spring.datasource.password=***
    spring.datasource.driverClassName=com.mysql.jdbc.Driver
  	
### oracle的配置

    spring.datasource.url=jdbc:oracle:thin:@127.0.0.1:1521:db_name
    spring.datasource.username=***
    spring.datasource.password=***
    spring.datasource.driverClassName=oracle.jdbc.driver.OracleDriver

### sqlserver的配置
     
    spring.datasource.url = jdbc:sqlserver://127.0.0.1:1433:DatabaseName=db_name
    spring.datasource.username=***
    spring.datasource.password=***
    spring.datasource.driver-class-name = com.microsoft.sqlserver.jdbc.SQLServerDriver
    

