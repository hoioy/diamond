参考链接：
http://www.itmuch.com/work/sonartype-deploy-mvn-depencency-to-maven-central/

zhaozhao申请：https://issues.sonatype.org/browse/OSSRH-56729

#### 配置本地maven仓库
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
