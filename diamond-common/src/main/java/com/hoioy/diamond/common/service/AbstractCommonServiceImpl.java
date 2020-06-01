package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;
import java.util.Optional;

/**
 * 内部使用不对外暴露的基础ICommonService实现
 * 统一配置缓存，在diamond-common-jpa[mybaits]中注意删除缓存，保持缓存数据一致性
 */
abstract class AbstractCommonServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends CommonDTO> implements ICommonService<DTO, D> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected Class<D> domainClass = (Class<D>) CommonReflectionUtil.getSuperClassGenericType(getClass(), 1);
    protected Class<DTO> dtoClass = (Class<DTO>) CommonReflectionUtil.getSuperClassGenericType(getClass(), 2);

    @Autowired
    protected I iBaseRepository;

    @Override
    public final Class<D> getDomainClass() {
        return domainClass;
    }

    @Override
    public final Class<DTO> getDTOClass() {
        return dtoClass;
    }

    public final DTO domainToDTO(D domain) {
        return domainToDTO(domain, true);
    }

    public final D dtoToDomain(DTO dto) {
        return dtoToDomain(dto, true);
    }

    public final List<DTO> domainListToDTOList(List<D> dList) {
        return domainListToDTOList(dList, true);
    }

    public final List<D> dtoListToDomainList(List<DTO> dtoList) {
        return dtoListToDomainList(dtoList, true);
    }

    // 获取PageDTO中过滤器条件转化为Domain
    protected D getDomainFilterFromPageDTO(final PageDTO pageDTO) {
        if (pageDTO.getFilters() == null) {
            pageDTO.setFilters(createDTO());
        }

        DTO filters = (DTO) pageDTO.getFilters();
        if (filters instanceof BaseDTO) {
            ((BaseDTO) filters).setFlag(1);
        }
        D main = dtoToDomain(filters);
        return main;
    }

    @Override
    @CacheEvict(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator")
    public abstract boolean removeById(String id);

    @Cacheable(value = CacheKey_dto, keyGenerator = "baseCacheKeyGenerator",sync = true, condition = "#result != null")
    @Override
    public abstract Optional<DTO> findById(String id) ;
}
