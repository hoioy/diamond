package com.hoioy.sample.conf;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableKnife4j
@ConditionalOnProperty(prefix = "swagger2", value = {"enable"}, havingValue = "true")
public class Swagger2Config implements WebMvcConfigurer {
    private final String apiKeyName = "普通登录或者OAuth2登录后获取的的Bearer Token";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("swagger-ui.html", "doc.html");
    }

    /**
     * 主要是这个方法，其他的方法是抽出去的，所以大家不要害怕为啥有这么多方法
     * 在 basePackage 里面写需要生成文档的 controller 路径
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.hoioy.diamond.sys.api"),
                        RequestHandlerSelectors.basePackage("com.hoioy.sample.api"),
                        RequestHandlerSelectors.basePackage("com.hoioy.diamond.security.api"),
                        RequestHandlerSelectors.basePackage("com.hoioy.diamond.security.jwt.api")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Lists.newArrayList(securityContextNormal()))
                .securitySchemes(Lists.<SecurityScheme>newArrayList(normalApiKey()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "开发框架",
                "获取swagger的token：" +
                        "curl -i -X POST -d \"username=admin&password=123456&grant_type=password&client_id=swagger&client_secret=swagger\" http://localhost:8769/oauth/token " +
                        "可以使用curl命令或者postman等发起post请求，获取token,然后粘贴到swagger的Authorize中，注意前面要加上Bearer 前缀，并且用空格隔开",
                "1.0.0",
                "http://localhost:7779/",
                new Contact("", "xxx", "xxx@email.com.cn"),
                "License of API", "API license URL", Collections.emptyList());
    }

    // 普通登录swagger
    private ApiKey normalApiKey() {
        return new ApiKey(apiKeyName, "Authorization", "header");
    }

    private SecurityContext securityContextNormal() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference(apiKeyName, authorizationScopes));
    }
}
