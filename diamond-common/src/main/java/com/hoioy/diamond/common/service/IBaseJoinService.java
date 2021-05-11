package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.annotation.BaseJoinId;
import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseJoinDTO;
import com.hoioy.diamond.common.util.JoinIDUtil;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 关联表基础service
 */
public interface IBaseJoinService<DTO extends BaseJoinDTO, D extends CommonDomain> extends IBaseCommonService<DTO, D> {

    default DTO beforeCreate(DTO dto) {
        IBaseCommonService.super.beforeCreate(dto);
        D domain = dtoToDomain(dto, true);
        dto.setId(JoinIDUtil.generateJoinId(domain, getDomainIdMethodGetMap()));
        return dto;
    }

    /**
     * domain中表示Id的属性,按照BaseJoinId顺序排列, 为了提升查询性能，不用每次都通过反射来获取
     */
    List<String> getDomainIdFieldNames();

    /**
     * domain中表示Id的属性相应的getXXId方法，为了提升查询性能，不用每次都通过反射来获取
     */
    Map<String, Method> getDomainIdMethodGetMap();

    List<String> findFirstIdsBySecondIds(List<String> secondIds);

    List<String> findSecondIdsByFirstIds(List<String> firstIds);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findFirstIdsBySecondId(String secondId);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findSecondIdsByFirstId(String firstId);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findIdsByJoinIds(List<String> joinIds, BaseJoinId.Index joinIndex);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    List<String> findIdsByJoinId(String joinId, BaseJoinId.Index joinIndex);
}
