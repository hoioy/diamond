package com.hoioy.jiayin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication(scanBasePackages = {"com.hoioy.diamond","com.hoioy.jiayin"})
@MapperScan(basePackages = {"com.hoioy.diamond.*.mapper","com.hoioy.jiayin.*.mapper"})
public class DiamondApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiamondApplication.class, args);
    }
}
