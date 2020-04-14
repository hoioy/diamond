package com.hoioy.diamond.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD,ElementType.TYPE  })
@Documented
public @interface OperationLogAnno {

	String module() default ""; // 模块名称

	String logInfo() default ""; // 方法描述

	String logClassName() default "";// 用于判断操作哪个实体类

	String  logOperationType() default "";// 1 添加 2 修改 3 删除 ,4查询,5登录，6登出

	String desc() default "";// 切面中要用到方法的描述信息
}
