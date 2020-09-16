package com.hoioy.diamond.common.base;

import cn.hutool.json.JSONUtil;
import com.hoioy.diamond.common.dto.BaseTreeDTO;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class BaseTreeServiceImpl<M extends IBaseTreeMapper<D>, D extends BaseTreeDomain, DTO extends BaseTreeDTO>
        extends BaseServiceImpl<M, D, DTO> implements IBaseTreeService<DTO, D> {

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @Override
    public List<DTO> findByParentId(String parentId) {
        QueryWrapper<D> ew = new QueryWrapper<>();
        ew.orderByAsc("order_index");
        if (StringUtils.isBlank(parentId)) {
            ew.isNull("parent_id");
        } else {
            ew.eq("parent_id", parentId);
        }
        String key = "findByParentId:" + domainClass.getName() + ":" + parentId;
        String cacheStr = commonRedisUtil.get(key);
        if (StringUtils.isNotEmpty(cacheStr)) {
            //有缓存，直接返回
            List<DTO> dtos = JSONUtil.toList(JSONUtil.parseArray(cacheStr), dtoClass);
            return dtos;
        } else {
            List<D> parents = iBaseRepository.selectList(ew);
            List<DTO> dtos = this.domainListToDTOList(parents);
            cacheStr = JSONUtil.toJsonStr(dtos);
            commonRedisUtil.set(key, cacheStr, 120l);//单位秒
            return dtos;
        }
    }
}
