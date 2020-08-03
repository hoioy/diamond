参考链接：
http://www.itmuch.com/work/sonartype-deploy-mvn-depencency-to-maven-central/

zhaozhao申请：https://issues.sonatype.org/browse/OSSRH-56729

#### 配置本地maven仓库
联系 andyzhaozhao 提供 账号与密码
```
<server>
  <!-- 这里的ID要和distributionManagement.repository.id一致 -->
  <id>ossrh</id>
  <!-- https://issues.sonatype.org/的账号 -->
  <username>账号</username>
  <!-- https://issues.sonatype.org/的密码 -->
  <password>密码</password>
</server>
```
#### GPG密钥安装
Windows 系统，可以下载 Gpg4win 软件来生成密钥对。https://www.gpg4win.org/download.html


### 注意pom重要配置
具体要求：https://central.sonatype.org/pages/requirements.html
* gpg加密。请联系andyzhaozhao获取
* javadoc和source

参考：https://blog.csdn.net/weixin_38096157/article/details/88951278?from=singlemessage