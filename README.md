### 环境依赖
##### 必选
- jdk 1.8 +
- mysql 8
- redis
- maven 3+
- lombok IDE插件([lombok的各种IDE插件安装](/docs/lombok的各种IDE插件安装.md))

##### 可选
- mybatis IDE插件

### 运行
1. 运行启动diamond项目
1. 默认管理员用户名/密码：admin/admin
1. Swagger访问路径：http://{ip}:{port}/swagger-ui.html 或者 http://{ip}:{port}/doc.html

### 构建
#### 编译
`mvn compile`

#### 打包
`mvn package -Dmaven.test.skip=true`

#### 安装到本地仓库
`mvn install -Dmaven.test.skip=true`

#### 包发布到太极中央仓库
`mvn deploy -Dmaven.test.skip=true`


