package com.hoioy.jiayin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.hoioy.diamond","com.hoioy.jiayin"})
@MapperScan(basePackages = {"com.hoioy.jiayin.mapper"})
//@MapperScan(basePackages = {"com.hoioy.diamond","com.hoioy.jiayin.mapper"})
public class JiayinAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiayinAppApplication.class, args);
    }

}
