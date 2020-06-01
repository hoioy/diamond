package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.service.IBaseTreeService;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseTreeServiceImpl<I extends IBaseTreeRepository<D>, D extends BaseTreeDomain, DTO extends BaseTreeDTO>
        extends BaseServiceImpl<I, D, DTO> implements IBaseTreeService<DTO, D> {

    @Override
    public List<DTO> findByParentId(String parentId) {
        List<D>  parents = iBaseRepository.findByParentId(parentId);
        List<DTO> dtos = this.domainListToDTOList(parents);
        return dtos;
    }

}
