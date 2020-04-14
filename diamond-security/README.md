## 代码结构
* com.hoioy.diamond.security.jwt包：前后端分离架构下，spring security的配置工具类
* com.hoioy.diamond.security包：详细配置方式参考 spring security教程博客

## 关于spring security在前后端分离配置方式说明

### 前后端分离描述
* cors跨域相关
* 重定向相关（如将重定向改为返回json）
* 使用HttpOnly属性
* 防止csrf攻击，判断reffer
* TODO

### jwt配置方式详细介绍
* 自定义filter等
* 使用的jwt包选择等
* TODO

应用通过一系列任务检查JWT的有效性。例如，检查签名是否正确；检查Token是否过期；检查Token的接收方是否是自己（可选）

和Session方式存储id的差异
Session方式存储用户id的最大弊病在于要占用大量服务器内存，对于较大型应用而言可能还要保存许多的状态。一般而言，大型应用还需要借助一些KV数据库和一系列缓存机制来实现Session的存储。

而JWT方式将用户状态分散到了客户端中，可以明显减轻服务端的内存压力。除了用户id之外，还可以存储其他的和用户相关的信息，例如该用户是否是管理员、用户所在的分桶（见[《你所应该知道的A/B测试基础》一文](/2015/08/27/introduction-to-ab-testing/）等。

虽说JWT方式让服务器有一些计算压力（例如加密、编码和解码），但是这些压力相比磁盘I/O而言或许是半斤八两。具体是否采用，需要在不同场景下用数据说话。

参考：
http://blog.leapoahead.com/2015/09/07/user-authentication-with-jwt/

### 普通登录自定义jwt token的生命周期
#### 一.token的生成
1. 调用AuthenticationController的“/auth”接口登录，登录成功后返回JWT格式的token。
1. TDF的JWT Token的payload里面存储了预置属性，用户也可以自定扩展属性，可以参考CustomJwtAccessTokenConverter类，预置属性有（参考JwtClaimDTO）；
    ```
    @Data
    @NoArgsConstructor
    public class JwtClaimDTO {
        private String subject;
        //创建时间
        private Date createdDate;
        //过期时间
        private Date expirationDate;
        //最近一次刷新时间
        private Date IssuedAt;
        //可更新截止时间
        private Date refreshDate;
    
        //以下是spring security oauth2的属性，参考：AccessTokenConverter
        //token的客户
        private String aud;
        //oauth2协议的client id
        private String client_id;
        //经常使用的，以数字时间定义失效期，也就是当前时间以后的某个时间本token失效。
        private Long exp;
        //JWT唯一标识. 能用于防止 JWT重复使用，一次只用一个token
        private String jti;
    }
    ```
注意其中的`expirationDate`和`refreshDate`。
* expirationDate：token过期时间，小于refreshDate
* refreshDate：可刷新截止时间，大于expirationDate

token过期了，但是如果token还在可刷新时间范围内，就可以给自动刷新token，
给token“续命”。

#### 二.token的自动刷新
每次调用后台接口都会经过tdf-security提供的过滤器 JwtAuthorizationTokenFilter。
JwtAuthorizationTokenFilter有自动刷新token的逻辑；
1. 解析传递过来的jwt token获取jwt中存储的数据并解析为JwtClaimDTO。
1. 获取到`expirationDate`和`refreshDate`
1. 执行自动刷新：
    1. 当前时间小于 refreshDate 说明token可以执行自动刷新
    1. 当前时间大于 expirationDate 说明token已经过期，执行自动刷新，更新`expirationDate`和`refreshDate`返回新的token
1. 前端接收到新的token，使用新token替换旧token。

#### 三.token的过期与消亡
1. 如果当前时间大于token的refreshDate，说明token已经彻底过期，消亡.
1. 前端主动删除token，消亡。