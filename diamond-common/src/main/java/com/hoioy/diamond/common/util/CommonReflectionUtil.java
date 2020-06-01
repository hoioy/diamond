package com.hoioy.diamond.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//反射方法
public class CommonReflectionUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonReflectionUtil.class);

    public static CommonReflectionUtil getInstance() {
        return CommonReflectionUtil.CommonReflectionUtilHolder.instance;
    }

    /**
     * <p>
     * 反射对象获取泛型
     * </p>
     *
     * @param clazz 对象
     * @param index 泛型所在位置
     * @return Class
     */
    public static Class<?> getSuperClassGenericType(final Class<?> clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            logger.warn(String.format("Warn: %s's superclass not ParameterizedType", clazz.getSimpleName()));
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            logger.warn(String.format("Warn: Index: %s, Size of %s's Parameterized Type: %s .", index,
                    clazz.getSimpleName(), params.length));
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(String.format("Warn: %s not set the actual class on superclass generic parameter",
                    clazz.getSimpleName()));
            return Object.class;
        }
        return (Class<?>) params[index];
    }

    private static class CommonReflectionUtilHolder {
        private static CommonReflectionUtil instance = new CommonReflectionUtil();
    }
}
