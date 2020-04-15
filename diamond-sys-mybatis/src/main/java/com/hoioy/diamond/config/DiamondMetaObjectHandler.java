package com.hoioy.diamond.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiamondMetaObjectHandler implements MetaObjectHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(DiamondMetaObjectHandler.class);

    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        LOGGER.info("start insert fill ....");
        this.setFieldValByName("createdDate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("flag", 1, metaObject);
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
    }

    // 更新的税后自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        boolean updateTime = metaObject.hasSetter("modifiedDate");
        this.setFieldValByName("modifiedDate", LocalDateTime.now(), metaObject);
    }
}
