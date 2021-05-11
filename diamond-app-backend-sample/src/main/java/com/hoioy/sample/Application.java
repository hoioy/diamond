package com.hoioy.sample;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.hoioy.sample", "com.hoioy.diamond"})
@MapperScan(basePackages = {"com.hoioy.sample.mapper","com.hoioy.diamond.log.mapper","com.hoioy.diamond.sys.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
