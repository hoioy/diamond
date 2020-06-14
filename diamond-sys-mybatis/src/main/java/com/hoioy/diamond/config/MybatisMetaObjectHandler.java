package com.hoioy.diamond.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisMetaObjectHandler.class);

    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createdDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("flag", 1, metaObject);
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
        String currentLogin = CommonSecurityUtils.getCurrentLogin();
        this.setFieldValByName("createdBy", currentLogin, metaObject);
        this.setFieldValByName("modifiedBy", currentLogin, metaObject);
    }

    // 更新的税后自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        String currentLogin = CommonSecurityUtils.getCurrentLogin();
        this.setFieldValByName("modifiedBy", currentLogin, metaObject);
        boolean updateTime = metaObject.hasSetter("modifiedDate");
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
    }
}
