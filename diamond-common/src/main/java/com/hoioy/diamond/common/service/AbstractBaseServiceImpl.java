package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.exception.CommonException;
import com.hoioy.diamond.common.validator.exception.ValidateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.UUID;

/**
 * 主表service抽象类，抽取公共抽象方法
 */
public abstract class AbstractBaseServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends BaseDTO>
        extends AbstractCommonServiceImpl<I, D, DTO> implements IBaseService<DTO, D> {

    @Override
    @CachePut(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    public abstract DTO update(DTO dto);

    @Override
    public void beforeRemove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ValidateException("ID集合不能为空");
        }
        for (String id : ids) {
            if (StringUtils.isBlank(id)) {
                throw new ValidateException("ID不能为空");
            }
        }
    }

    @Override
    public DTO beforeSave(DTO dto) {
        if (!StringUtils.isBlank(dto.getId())) {
            throw new CommonException("此方法只能用于新增操作，更新请调用update方法");
        }
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        dto.setId(id);
        dto.setFlag(1);// 初始化
        return dto;
    }
}
