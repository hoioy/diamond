package com.hoioy.jiayin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class JiayinApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiayinApplication.class, args);
    }
}
