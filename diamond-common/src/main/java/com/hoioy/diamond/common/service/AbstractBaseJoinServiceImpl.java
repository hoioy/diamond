package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.common.util.CommonStatic;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 关联表service抽象类，抽取公共抽象方法
 */
public abstract class AbstractBaseJoinServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends BaseJoinDTO>
        extends AbstractCommonServiceImpl<I, D, DTO> implements IBaseJoinService<DTO, D> {
    //domain中表示Id的属性,按照BaseJoinId顺序排列, 为了提升查询性能，不用每次都通过反射来获取
    protected List<String> domainIdFieldNames = Arrays.asList("", "", "", "", "");
    //domain中表示Id的属性相应的getXXId方法，为了提升查询性能，不用每次都通过反射来获取
    protected Map<String, Method> domainIdMethodGetMap = new HashMap<>();

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    @CacheEvict(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    public abstract boolean remove(DTO dto);

    @Override
    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", sync = true, condition = "#result != null")
    public abstract List<String> findFirstIdsBySecondId(String secondId);

    @Override
    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", sync = true, condition = "#result != null")
    public abstract List<String> findSecondIdsByFirstId(String firstId);

    @Override
    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", sync = true, condition = "#result != null")
    public abstract List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex);

    @Override
    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", sync = true, condition = "#result != null")
    public abstract List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex);

    //获取id属性对应的getXXId方法
    protected final Method getIdFieldGetMethod(String idFieldName) {
        Method method = domainIdMethodGetMap.get(idFieldName);
        if (method == null) {
            try {
                String firstLetter = idFieldName.substring(0, 1).toUpperCase();
                String getter = "get" + firstLetter + idFieldName.substring(1);
                method = getDomainClass().getMethod(getter, new Class[]{});
                domainIdMethodGetMap.put(idFieldName, method);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    //调用domain的id属性对应的getXXId方法
    protected final String callIdFieldGetMethod(D domain, String idFieldName) {
        try {
            Method method = getIdFieldGetMethod(idFieldName);
            Object value = method.invoke(domain, new Object[]{});
            return (String) value;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    //生成关联表名称
    protected final String generateJoinId(D domain) {
        Class<D> domainClass = getDomainClass();

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
            if (qw.index().ordinal() >= CommonStatic.MaxJoinTableNum) {
                throw new CommonException("BaseJoinId的index属性大于等于{}关联表不支持{}以上表关联，建议您重新设计Domain。同时注意BaseJoinId的" +
                        "index属性是否从0开始，依次累加", CommonStatic.MaxJoinTableNum, CommonStatic.MaxJoinTableNum);
            }
            String value = callIdFieldGetMethod(domain, field.getName());
            indexIds.set(qw.index().ordinal(), value);
        }

        if (CollectionUtils.isEmpty(indexIds)) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        indexIds.forEach(id -> {
            result.append(id);
        });

        return result.toString();
    }

    //根据BaseJoinId注解获取对应顺序的名称
    protected final String getIdFieldNameByIndex(BaseJoinId.Index index) {
        String idFieldName = domainIdFieldNames.get(index.ordinal());
        if (StringUtils.isBlank(idFieldName)) {
            Class<D> domainClass = getDomainClass();
            List<Field> fields = Arrays.asList(ReflectUtil.getFields(domainClass));
            for (Field field : fields) {
                //获取字段上的 @BaseJoinId 注解
                BaseJoinId qw = field.getAnnotation(BaseJoinId.class);
                if (qw == null) {
                    continue;
                }
                if (qw.index() == index) {
                    domainIdFieldNames.set(index.ordinal(), field.getName());
                    return field.getName();
                }
            }
        }

        //对应的index没有找到属性名
        return idFieldName;
    }

    protected final void deleteCache() {
        //有删除操作，则直接删除所有 findIdsByMenuUrl 缓存
        commonRedisUtil.removeByPattern(CacheKey_dto + "::" + getDTOClass().getSimpleName() + "*");
    }
}
