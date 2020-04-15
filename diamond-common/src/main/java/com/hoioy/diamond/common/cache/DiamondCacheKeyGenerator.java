package com.hoioy.diamond.common.cache;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Diamond框架定义的缓存Key生成器
 */
public class DiamondCacheKeyGenerator implements KeyGenerator {
    /**
     * 生成缓存自定义key
     * target：调用该方法的方法名
     * method：该方法的所在类名
     * params：传入值的参数值
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {

        Map<String, Object> map = new HashMap<String, Object>();
        /**
         * 放入target的名字
         * key值预览："people4::{"target":"public class com.hoioy.diamond.sys.utils.RedisUtil"}"
         */
        map.put("target", target.getClass().toGenericString());
        /**
         * 放入method的名字
         * key值预览："people4::{"method":"getDataItemAll","target":"public class com.hoioy.diamond.sys.utils.RedisUtil"}"
         */
        //map.put("method", method.getName());
        /**
         * 把所有参数放进去
         * key值预览："people4::{"params-0":{"page":"ake"},"target":"public class com.hoioy.diamond.sys.utils.RedisUtil"}"
         *  4)  "userName::123456"
         */
        /*if (params != null && params.length > 0) {
            int i = 0;
            for (Object o : params) {
                map.put("params-" + i, o);
                i++;
            }
        }*/
        //json转String
        return JSONObject.toJSON(map).toString();
    }
}
