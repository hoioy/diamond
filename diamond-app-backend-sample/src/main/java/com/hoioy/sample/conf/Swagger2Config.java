package com.hoioy.sample.conf;

import cn.hutool.core.collection.ListUtil;
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
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class Swagger2Config implements WebMvcConfigurer {
    private final String apiKeyName = "普通登录或者OAuth2登录后获取的的Bearer Token";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "index.html");
        registry.addRedirectViewController("swagger-ui.html", "doc.html");
    }

    /**
     * 主要是这个方法，其他的方法是抽出去的
     * 在 basePackage 里面写需要生成文档的 controller 路径
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hoioy.diamond.sys.api")
                        .or(RequestHandlerSelectors.basePackage("com.hoioy.sample.api"))
                        .or(RequestHandlerSelectors.basePackage("com.hoioy.diamond.log.api"))
                        .or(RequestHandlerSelectors.basePackage("com.hoioy.diamond.security.api"))
                        .or(RequestHandlerSelectors.basePackage("com.hoioy.diamond.security.jwt.api")))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(ListUtil.of(securityContextNormal()))
                .securitySchemes(ListUtil.of(normalApiKey()));
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                "开发框架",
                "获取swagger的token：" +
                        "第一步-获取验证码：curl http://localhost:7779/captcha    " +
                        "第二步-模拟登录获取token：curl -i -X POST -d \"username=admin&password=admin&captchaCode={上一步获取的验证码}&captchaKey={上一步获取的key}\" http://localhost:7779/login  " +
                        "第三步-可以使用curl命令或者postman等发起post请求，获取token,然后粘贴到swagger的Authorize中",
                "1.0.1",
                "http://localhost:7779/",
                new Contact("zhaozhao", "http://hoioy.com", ""),
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
        AuthorizationScope[] authorizationScopes = (AuthorizationScope[]) Arrays.asList(
                new AuthorizationScope("global", "accessEverything")).toArray();
        return ListUtil.of(new SecurityReference(apiKeyName, authorizationScopes));
    }
}
