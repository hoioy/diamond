package com.hoioy.diamond;

import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.sys.service.IDataItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    @Autowired
    private IDataItemService dataItemService;

    /**
     * 新增、编辑更新dataItem（字典分类栏）缓存
     */
    //@CachePut(value="DataItem", keyGenerator="cacheKeyGenerator")
    public PageDTO setDataItemAll(PageDTO dto) {
        log.info("RedisUtil-setDataItemAll-CachePut更新缓存");
        return dataItemService.getPage(dto);
    }

    /**
     * 查询时获取dataItem（字典分类栏）缓存
     */
    //@Cacheable(value="DataItem",keyGenerator="cacheKeyGenerator")
    public PageDTO getDataItemAll(PageDTO dto) {
        log.info("RedisUtil-getDataItemAll-error-CacheAble未从缓存读取数据");
        return dataItemService.getPage(dto);
    }
}
