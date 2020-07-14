# TDF中jwt登录逻辑的讲解

## 1.登录流程

进入前台登录页面，首先会请求验证码接口：http://127.0.0.1:7779/captcha 如下图：

![](image/tdf登录-验证码.jpg)

captcha该接口是不需要登录就可以访问的，无需登录的配置如下图中配置，permitAll()配置后，即可直接访问该接口。

![](image/tdf登录-验证码后台权限.jpg)

输入用户名、密码、验证码点击登录，会执行以下几个方法，首先是：http://127.0.0.1:7779/auth，该方法将用户名，密码，验证码作为入参，如果用户名密码正确，从后台获取token，正确的返回值格式如下：其中data中是token

```json
{
  "code" : 1,
  "message" : "get token success!",
  "data" : "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWJqZWN0IjoiYWRtaW4iLCJjcmVhdGVkRGF0ZSI6MTU4NTYzMjIzMzkyMywiZXhwaXJhdGlvbkRhdGUiOjE1ODU2NjgyMzM5MjMsInJlZnJlc2hEYXRlIjoxNTg1NzA0MjMzOTIzLCJhdWQiOm51bGwsImNsaWVudF9pZCI6bnVsbCwiZXhwIjpudWxsLCJqdGkiOm51bGwsImlzc3VlZEF0IjpudWxsfQ.rYjrqH3OmwYQUO6sA1Lp_7ZTu-buV7-0WQnPl_gDyss"
}
```

前台获取到token后，封装成如下形式：

```vue
 token = { 'access_token': token, 'create_timestamp': new Date().getTime() }
```

分别利用vuex和cookie进行token的存储。如下：

![image-20200403104314353](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200403104314353.png)

```vue
SET_TOKEN: (state, token) => {
      if (token) {
        // 使用oauth2第三方登录，存储复杂token结构暂时没有用到，目前只用到了access_token
        // andyzhao changed 20191127 使用复杂token格式
        if (token instanceof Object) {
          // 如果是通过统一认证服务登录
          token.create_timestamp = new Date().getTime()// 时间戳，精确到了毫秒
        } else {
          console.info('jwt普通登录')
          token = { 'access_token': token, 'create_timestamp': new Date().getTime() }
        }
        setToken(token)
        state.token = token
      } else {
        removeToken()
        state.token = token
      }
    },
```

```vue
export function setToken(token) {
  if (token) {
    token = JSON.stringify(token)
  }
  return Cookies.set(TokenKey, token)
}
```

然后接下来每次请求的时候，会有一个拦截器中将请求的header中添加token，用于后台校验，代码如下：

![image-20200403104427994](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200403104427994.png)

```vue
service.interceptors.request.use(
  async request => {
    request.headers['Access-Control-Allow-Origin'] = '*'
    // 后端服务jwt token信息
    var tmpToken = store.getters.token
    if (!tmpToken) {
      tmpToken = Cookies.get('USER_TOKEN')
    }
    if (tmpToken) { // 如果获取到token，加工成后台需要的格式Bearer
      request.headers['Authorization'] = 'Bearer ' + tmpToken.access_token
    }
    return request
  },
  error => {
    // 请求发生错误
    console.log('request interceptor error') // for debug
    Promise.reject(error)
  }
)
```

后台每次接收到请求后，会在tdf-security中解析token，并进行校验

```java
private String extractToken(HttpServletRequest request) {
    String authToken = request.getParameter("token");
    if (StringUtils.isEmpty(authToken)) {
        //如果parameter中没有，再从header中取
        final String requestHeader = request.getHeader(tokenHeader);
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);
        } else {
            logger.warn("couldn't find bearer string, will ignore the header");
        }
    }
    return authToken;
}
```

## 2.普通jwt登录中token的刷新和过期

### 2.1.token的生成
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
* 其中refreshDate和expirationDate两个时间的定义在这个文件中
* ![image-20200403103110267](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200403103110267.png)

token过期了，但是如果token还在可刷新时间范围内，就可以给自动刷新token，
给token“续命”。

### 2.2.token的自动刷新
每次调用后台接口都会经过tdf-security提供的过滤器 JwtAuthorizationTokenFilter。
JwtAuthorizationTokenFilter有自动刷新token的逻辑；
1. 解析传递过来的jwt token获取jwt中存储的数据并解析为JwtClaimDTO。
1. 获取到`expirationDate`和`refreshDate`
1. 执行自动刷新：
    1. 当前时间小于 refreshDate 说明token可以执行自动刷新
    1. 当前时间大于 expirationDate 说明token已经过期，执行自动刷新，更新`expirationDate`和`refreshDate`返回新的token
1. 前端接收到新的token，使用新token替换旧token。

### 2.3.token的过期与消亡
1. 如果当前时间大于token的refreshDate，说明token已经彻底过期，消亡.

2. 前端主动删除token，消亡。

   前端代码如下：

![image-20200403104800389](C:\Users\admin\AppData\Roaming\Typora\typora-user-images\image-20200403104800389.png)

```vue
if (res.message) {
              Message({
                message: res.message,
                type: 'error',
                duration: 3 * 1000
              }).then(() => {
                store.dispatch('FedLogOut').then(() => {
                  location.reload() // 重新实例化vue-router对象 避免bug
                })
              })
            }
```

