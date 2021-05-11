package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.domain.ICommonRepository;
import com.hoioy.diamond.common.dto.BaseDTO;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.common.util.CommonReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import java.util.List;

/**
 * 内部使用不对外暴露的基础ICommonService实现
 */
public abstract class AbstractCommonServiceImpl<I extends ICommonRepository<D>, D extends CommonDomain, DTO extends CommonDTO> implements ICommonService<DTO, D> {
    protected Logger log = LoggerFactory.getLogger(getClass());
    protected Class<D> domainClass = (Class<D>) CommonReflectionUtil.getSuperClassGenericType(getClass(), 1);
    protected Class<D> plusMapperClass = (Class<D>) CommonReflectionUtil.getSuperClassGenericType(getClass(), 0);
    protected Class<DTO> dtoClass = (Class<DTO>) CommonReflectionUtil.getSuperClassGenericType(getClass(), 2);

    @Autowired
    protected CommonCacheUtil commonCacheUtil;

    @Autowired
    protected I iBaseRepository;

    @Autowired
    protected MessageSource messageSource;

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

    /**
     * 获取PageDTO中过滤器条件转化为Domain
     */
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

    protected final void deleteCache() {
        //有删除操作，则直接删除所有 findIdsByMenuUrl 缓存
        commonCacheUtil.removeByPattern(CacheKey_dto + "::" + getDTOClass().getSimpleName() + "*");
    }
}
