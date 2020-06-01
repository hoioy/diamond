package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;

import java.util.UUID;

/**
 * 主表service抽象类，抽取公共抽象方法
 */
public abstract class AbstractBaseServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends BaseDTO>
        extends AbstractCommonServiceImpl<I, D, DTO> implements IBaseService<DTO, D> {

    @Override
    @CachePut(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    public abstract DTO update(DTO dto);

    protected DTO beforeSave(DTO dto) {
        if (!StringUtils.isEmpty(dto.getId())) {
            throw new CommonException("此方法只能用于新增操作，更新请调用update方法");
        }
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        dto.setId(id);
        dto.setFlag(1);// 初始化
        return dto;
    }
}
