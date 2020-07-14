package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonJpaPageUtil;
import com.hoioy.diamond.sys.domain.DataItem;
import com.hoioy.diamond.sys.domain.DataItemRepository;
import com.hoioy.diamond.sys.dto.DataItemDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDataItemService;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class DataItemServiceImpl extends BaseTreeServiceImpl<DataItemRepository, DataItem, DataItemDTO> implements IDataItemService<DataItem> {

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        List<DataItem> children = iBaseRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(root.<String>get("parentId").in(ids));
            }
        });
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException("所选数据下面含有子元素集合，不能删除！需要先删除子元素");
        }
    }

	@Override
	public PageDTO<DataItemDTO> findDataItemByTypePageable(PageDTO<DataItemDTO> pageDTO) {
        PageRequest pageable = CommonJpaPageUtil.getInstance().toPageRequest(pageDTO);
        Page<DataItemDTO> pageList = iBaseRepository.findDataItemPageable(pageDTO.getFilters(), pageable);
        pageDTO.setTotal(pageList.getTotalElements());
        pageDTO.setList(pageList.getContent());
        return pageDTO;
	}
}
