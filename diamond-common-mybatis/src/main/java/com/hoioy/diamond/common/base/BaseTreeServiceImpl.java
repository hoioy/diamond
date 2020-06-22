package com.hoioy.diamond.common.base;

import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.service.IBaseTreeService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public abstract class BaseTreeServiceImpl<M extends IBaseTreeMapper<D>, D extends BaseTreeDomain, DTO extends BaseTreeDTO>
        extends BaseServiceImpl<M, D, DTO> implements IBaseTreeService<DTO, D> {


    @Override
    public List<DTO> findByParentId(String parentId) {
        QueryWrapper<D> ew = new QueryWrapper<>();
        List<D> parents;
        if (StringUtils.isEmpty(parentId)) {
            ew.isNull("parent_id");
            parents = iBaseRepository.selectList(ew);
        } else {
            ew.eq("parent_id", parentId);
            parents = iBaseRepository.selectList(ew);
        }
        List<DTO> dtos = this.domainListToDTOList(parents);
        return dtos;
    }
}
