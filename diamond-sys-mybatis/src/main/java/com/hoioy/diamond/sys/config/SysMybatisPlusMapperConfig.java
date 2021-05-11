package com.hoioy.diamond.sys.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = {"com.hoioy.diamond.*.mapper"})
public class SysMybatisPlusMapperConfig {

}