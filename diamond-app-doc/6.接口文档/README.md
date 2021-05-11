#接口文档
##swagger接口API访问路径

swagger接口API访问路径为：`http[s]://[ip:port]/swagger-ui.html`
或者`http[s]://[ip:port]/doc.html  `


因为使用jwt为安全验证方式，所以大部分接口需要在请求时带有token，需要在swagger中获取权限，获取权限的方式如下：

前后端分离开发模式下，前端人员与后端人员通过swagger来沟通接口。
diamond sample后端强调安全性，后端接口默认是被基于Spring Security的Diamond Security模块保护的，很多接口需要登录后才能调用，否则会报401的错误。
1. 登录获取token
    1. 第一步-获取验证码：`curl http://localhost:7779/captcha `
    1. 第二步-模拟登录获取token：`curl -i -X POST -d \"username=admin&password=admin&captchaCode={上一步获取的验证码}&captchaKey={上一步获取的key}\" http://localhost:7779/login`  
    1. 第三步-可以使用curl命令或者postman等发起post请求，获取token,然后粘贴到swagger的Authorize中,
1. 此时我们就在所有Http接口请求的Header中默认增加了一个key为`Authorize`，并且value为具体token值的键值对。
我们就可以调用其他接口进行调试了。

