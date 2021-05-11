package com.hoioy.diamond.log.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = {"com.hoioy.diamond.log.mapper"})
public class LogMybatisPlusMapperConfig {

}