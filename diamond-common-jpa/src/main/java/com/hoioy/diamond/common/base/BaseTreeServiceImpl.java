package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.service.IBaseTreeService;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseTreeServiceImpl<I extends IBaseTreeRepository<D>, D extends BaseTreeDomain, DTO extends BaseTreeDTO>
        extends BaseServiceImpl<I, D, DTO> implements IBaseTreeService<DTO, D> {

    @Override
    public List<DTO> findByParentId(String parentId) {
        if(StringUtils.isBlank(parentId)){
            //JPA只支持null
            parentId = null;
        }
        List<D> parents = iBaseRepository.findByParentId(parentId);
        List<DTO> dtos = this.domainListToDTOList(parents);
        return dtos;
    }

}
