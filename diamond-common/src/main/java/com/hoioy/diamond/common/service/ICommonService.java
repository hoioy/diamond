package com.hoioy.diamond.common.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.CommonDTO;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonBeanUtil;

import java.util.ArrayList;
import java.util.List;

public interface ICommonService<DTO extends CommonDTO, D extends CommonDomain> {
    String CacheKey_dto = "CommonDTOCache";

    Class<D> getDomainClass();

    Class<DTO> getDTOClass();

    default D createDomain() {
        try {
            D domain = getDomainClass().newInstance();
            return domain;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    default DTO createDTO() {
        try {
            DTO dto = getDTOClass().newInstance();
            return dto;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    default DTO domainToDTO(D domain, Boolean isCopyEmptyField) {
        if (domain == null) {
            return null;
        }
        DTO dto = createDTO();
        if (isCopyEmptyField) {
            CommonBeanUtil.saveCopy(domain, dto);
        } else {
            CommonBeanUtil.updateCopy(domain, dto);
        }
        return dto;
    }

    default D dtoToDomain(DTO dto, Boolean isCopyEmptyField) {
        if (dto == null) {
            return null;
        }
        D t = createDomain();
        if (isCopyEmptyField) {
            CommonBeanUtil.saveCopy(dto, t);
        } else {
            CommonBeanUtil.updateCopy(dto, t);
        }
        return t;
    }

    default List<DTO> domainListToDTOList(List<D> dList, Boolean isCopyEmptyField) {
        List<DTO> dtoList = new ArrayList();
        dList.forEach(d ->
                dtoList.add(domainToDTO(d, isCopyEmptyField))
        );
        return dtoList;
    }

    default List<D> dtoListToDomainList(List<DTO> dtoList, Boolean isCopyEmptyField) {
        List<D> dList = new ArrayList();
        dtoList.forEach(dto -> {
            dList.add(dtoToDomain(dto, isCopyEmptyField));
        });
        return dList;
    }

    PageDTO<DTO> getPage(final PageDTO<DTO> pageDTO);
}
