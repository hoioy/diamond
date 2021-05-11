package com.hoioy.diamond.common.service;


import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.BaseCommonDTO;
import com.hoioy.diamond.common.exception.CommonException;
import cn.hutool.core.util.StrUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Base层基础service
 */
public interface IBaseCommonService<DTO extends BaseCommonDTO, D extends CommonDomain> extends ICommonService<DTO, D> {
    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator", unless = "#result==null")
    DTO findById(String id);

    List<DTO> findByIds(List<String> ids);

    @Transactional(rollbackFor = Exception.class)
    default DTO beforeCreate(DTO dto) {
        if (dto == null) {
            throw new CommonException("参数不能为null");
        }
        if (StrUtil.isNotBlank(dto.getId())) {
            throw new CommonException("新增时候Id属性不能有值,此方法只能用于新增操作，更新请调用update方法");
        }

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean beforeBatchCreate(List<DTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new CommonException("要新增的集合不能为空");
        }

        for (DTO dto : dtoList) {
            beforeCreate(dto);
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    default DTO create(DTO dto) {
        return beforeCreate(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean batchCreate(List<DTO> dtoList) {
        return beforeBatchCreate(dtoList);
    }

    @Transactional(rollbackFor = Exception.class)
    default DTO afterCreate(DTO dto) {
        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean afterBatchCreate(List<DTO> dtoList) {
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean beforeRemove(String id) {
        if (StrUtil.isBlank(id)) {
            throw new CommonException("ID不能为空");
        }

        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean beforeBatchRemove(List<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new CommonException("要删除的ID集合不能为空");
        }
        for (String id : ids) {
            beforeRemove(id);
        }
        return true;
    }

    @CacheEvict(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    @Transactional(rollbackFor = Exception.class)
    default Boolean removeById(String id) {
        return beforeRemove(id);
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean removeByIds(List<String> ids) {
        return beforeBatchRemove(ids);
    }

    @CacheEvict(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    @Transactional(rollbackFor = Exception.class)
    default Boolean remove(DTO dto) {
        return removeById(dto.getId());
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean batchRemove(List<DTO> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) {
            throw new CommonException("要删除的对象集合不能为空");
        }

        List<String> idList = new ArrayList<>();
        dtoList.forEach(t -> {
            idList.add(t.getId());
        });

        return removeByIds(idList);
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean afterRemove(String id) {
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    default Boolean afterBatchRemove(List<String> ids) {
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    default DTO beforeUpdate(DTO dto) {
        if (dto != null && StrUtil.isBlank(dto.getId())) {
            throw new CommonException("ID不能为空");
        }

        return dto;
    }

    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    default DTO update(DTO dto) {
        return beforeUpdate(dto);
    }

    @Transactional(rollbackFor = Exception.class)
    default DTO afterUpdate(DTO dto) {
        return dto;
    }
}
