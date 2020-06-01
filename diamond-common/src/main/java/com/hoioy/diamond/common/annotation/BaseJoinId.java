package com.hoioy.diamond.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 关联表Id字段
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface BaseJoinId {
    // 多个主表ID字段的顺序，从0开始
    Index index() default Index.first;

    enum Index {
        first,
        second,
        third,
        fourth,
        Fifth;
    }
}
