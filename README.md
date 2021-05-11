### 工程地址
* 码云：https://gitee.com/hoioy/diamond.git
* Github： https://github.com/hoioy/diamond.git

### 演示环境
* http://diamond.hoioy.com/
* admin/admin

### 环境依赖
##### 必选
- jdk 1.8 +
- maven 3+
- lombok IDE插件([lombok的各种IDE插件安装](/docs/lombok的各种IDE插件安装.md))

##### 可选
- mysql 8
- redis

### 运行
* 方式一:
    1. 下载jar运行：https://gitee.com/hoioy/diamond/releases/v1.0.1  
    1. java -jar diamond-app-backend-sample-1.0.1.jar
* 方式一:
    1. 下载源码，等待maven自动构建完成
    1. 直接运行启动diamond-app-backend-sample
1. 默认管理员用户名/密码：admin/admin
1. 访问管理端：http://{ip}:{port}
1. 访问后端swagger接口文档：http://{ip}:{port}/doc.html
1. 访问后端h2数据库http://{ip}:{port}/h2-console
    * 数据库路径：~/db/diamondsample
    * 用户名/密码：sa/sa

### 构建
#### 编译
`mvn compile`

#### 打包
`mvn package -Dmaven.test.skip=true`

#### 安装到本地仓库
`mvn install -Dmaven.test.skip=true`

#### 包发布到中央仓库
`mvn deploy -Dmaven.test.skip=true`


