package com.hoioy.diamond.sys.web;

import com.hoioy.diamond.sys.service.IFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Component
public class BaseWebMvcConfigurer implements WebMvcConfigurer {

    public static String secondLevelDirectory = "FileStorageController";
    public static String secondLevelImgDirectory = "FileStorageControllerImg";
    public static String secondLevelImgUrl = "file/img";

    @Autowired
    private IFileStorageService iFileStorageService;

    // 支持通过URL访问文件
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String mImagesPath = iFileStorageService.getFileStorageRootPath();
        registry.addResourceHandler("/" + secondLevelImgUrl + "/**").addResourceLocations("file:" + mImagesPath + secondLevelImgDirectory + File.separator);
    }
}
