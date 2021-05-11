package com.hoioy.diamond.common.config;

import com.hoioy.diamond.common.service.CommonSecurityService;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisPlusMetaObjectHandler.class);

    // 新增时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createdDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("flag", 1, metaObject);
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
        String currentLogin = CommonSecurityService.instance.getCurrentLoginName();
        this.setFieldValByName("createdBy", currentLogin, metaObject);
        this.setFieldValByName("modifiedBy", currentLogin, metaObject);
    }

    // 更新时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        String currentLogin = CommonSecurityService.instance.getCurrentLoginName();
        this.setFieldValByName("modifiedBy", currentLogin, metaObject);
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
    }
}
