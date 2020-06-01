package com.hoioy.diamond.common.cache;

import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.service.ICommonService;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;

/**
 * 框架定义的缓存Key生成器
 */
public class BaseCacheKeyGenerator implements KeyGenerator {
    /**
     * 生成缓存自定义key
     * target：调用该方法的方法名
     * method：该方法的所在类名
     * params：传入值的参数值
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        //获取DTO类名
        String dtoClassName = ((ICommonService) target).getDTOClass().getSimpleName();
        String id = "";
        //如果参数是DTO,则获取DTO的id值作为key
        if (params[0] instanceof CommonDTO) {
            id = ((CommonDTO) params[0]).getId();
        } else if (params[0] instanceof String) {
            id = (String) params[0];
        }
        return CommonRedisUtil.getCacheKey(dtoClassName, id);
    }
}
