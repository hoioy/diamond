package com.hoioy.diamond;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@EnableCaching
@SpringBootApplication
public class TdfSampleJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(TdfSampleJwtApplication.class, args);
    }
}
