package com.hoioy.diamond.common.service;


import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.dto.DiamondDTO;
import com.hoioy.diamond.common.util.DiamondBeanUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 两个表关联表基础service
 */
public interface IDiamondService<DTO extends DiamondDTO, D extends DiamondDomain> {
    Class<D> getDomainClass();

    Class<DTO> getDTOClass();

    default D createDomain() {
        try {
            D domain = getDomainClass().newInstance();
            return domain;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    default DTO createDTO() {
        try {
            DTO domain = getDTOClass().newInstance();
            return domain;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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
            DiamondBeanUtil.saveCopy(domain, dto);
        } else {
            DiamondBeanUtil.updateCopy(domain, dto);
        }
        return dto;
    }

    default D dtoToDomain(DTO dto, Boolean isCopyEmptyField) {
        if (dto == null) {
            return null;
        }
        D t = createDomain();
        if (isCopyEmptyField) {
            DiamondBeanUtil.saveCopy(dto, t);
        } else {
            DiamondBeanUtil.updateCopy(dto, t);
        }
        return t;
    }

    default List<DTO> domainListToDTOList(List<D> dList, Boolean isCopyEmptyField) {
        List<DTO> dtoList = new ArrayList();
        dList.forEach(d -> {
            dtoList.add(domainToDTO(d, isCopyEmptyField));
        });
        return dtoList;
    }

    default List<D> dtoListToDomainList(List<DTO> dtoList, Boolean isCopyEmptyField) {
        List<D> dList = new ArrayList();
        dtoList.forEach(dto -> {
            dList.add(dtoToDomain(dto, isCopyEmptyField));
        });
        return dList;
    }
}
