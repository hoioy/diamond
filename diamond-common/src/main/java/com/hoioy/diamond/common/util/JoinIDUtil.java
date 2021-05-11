package com.hoioy.diamond.common.util;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.CommonException;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

//关联表的ID相关的工具类
//TODO zhaozhao关联表优化
public class JoinIDUtil {
    private static final Logger logger = LoggerFactory.getLogger(JoinIDUtil.class);

    public static JoinIDUtil getInstance() {
        return JoinIDUtil.JoinIDUtilHolder.instance;
    }

    /**
     * 调用domain的id属性对应的getXXId方法
     */
    public static <D extends CommonDomain> String callIdFieldGetMethod(D domain, Map<String, Method> methodMap, String idFieldName) {
        try {
            Method method = getIdFieldGetMethod(idFieldName, methodMap, domain.getClass());
            Object value = method.invoke(domain, new Object[]{});
            return (String) value;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 生成关联表名称
     */
    public static <DTO extends BaseJoinDTO, D extends CommonDomain> String generateJoinId(D domain, Map<String, Method> methodMap) {
        Class domainClass = domain.getClass();

        List<String> indexIds = Arrays.asList("", "", "", "", "");
        List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
        for (Field field : fields) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;
            }
            //获取字段上的 @BaseJoinId 注解
            BaseJoinId qw = field.getAnnotation(BaseJoinId.class);
            if (qw == null) {
                continue;
            }
            if (qw.index().ordinal() >= CommonStatic.MAX_JOIN_TABLE_NUM) {
                throw new CommonException("BaseJoinId的index属性大于等于{}关联表不支持{}以上表关联，建议您重新设计Domain。同时注意BaseJoinId的" +
                        "index属性是否从0开始，依次累加", CommonStatic.MAX_JOIN_TABLE_NUM, CommonStatic.MAX_JOIN_TABLE_NUM);
            }
            String value = callIdFieldGetMethod(domain, methodMap, field.getName());
            indexIds.set(qw.index().ordinal(), value);
        }

        if (CollectionUtils.isEmpty(indexIds)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        indexIds.forEach(id ->
                result.append(id)
        );

        return result.toString();
    }


    /**
     * 获取id属性对应的getXXId方法
     *
     * @param idFieldName 字段名称
     * @return
     */
    public static <D extends CommonDomain> Method getIdFieldGetMethod(String idFieldName, Map<String, Method> methodMap, Class<D> domainClass) {
        Method method = methodMap.get(idFieldName);
        if (method == null) {
            try {
                String firstLetter = idFieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + idFieldName.substring(1);
                method = domainClass.getMethod(getter, new Class[]{});
                methodMap.put(idFieldName, method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    /**
     * 根据BaseJoinId注解获取对应顺序的名称
     *
     * @param index
     * @return
     */
    public static <D extends CommonDomain> String getIdFieldNameByIndex(BaseJoinId.Index index,
                                                                        List<String> idFieldNames, Class<D> domainClass) {
        String idFieldName = idFieldNames.get(index.ordinal());
        if (StrUtil.isBlank(idFieldName)) {
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
            for (Field field : fields) {
                //获取字段上的 @BaseJoinId 注解
                BaseJoinId qw = field.getAnnotation(BaseJoinId.class);
                if (qw == null) {
                    continue;
                }
                if (qw.index() == index) {
                    idFieldNames.set(index.ordinal(), field.getName());
                    return field.getName();
                }
            }
        }

        //对应的index没有找到属性名
        return idFieldName;
    }

    private static class JoinIDUtilHolder {
        private static JoinIDUtil instance = new JoinIDUtil();
    }
}
