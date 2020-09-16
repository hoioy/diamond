package com.hoioy.diamond.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan(basePackages = {"cn.com.taiji.*.mapper"})
public class SysMybatisPlusMapperConfig {

}