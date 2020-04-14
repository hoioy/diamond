该子项目主要给tdf_sys_web、TDF_security等项目提供数据模型、数据访问服务，是整个系统的基础项目。    
### mysql 的配置
```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/db_name?useUnicode=true&characterEncoding=utf-8
spring.datasource.username=***
spring.datasource.password=***
spring.datasource.driverClassName=com.mysql.jdbc.Driver
```

### oracle的配置
```
spring.datasource.url=jdbc:oracle:thin:@127.0.0.1:1521:db_name
spring.datasource.username=***
spring.datasource.password=***
spring.datasource.driverClassName=oracle.jdbc.driver.OracleDriver
```

### sqlserver的配置
```
spring.datasource.url = jdbc:sqlserver://127.0.0.1:1433:DatabaseName=db_name
spring.datasource.username=***
spring.datasource.password=***
spring.datasource.driver-class-name = com.microsoft.sqlserver.jdbc.SQLServerDriver
```    
   

