package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.hoioy.diamond.sys.domain.DeptInfoRepository;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import com.hoioy.diamond.sys.service.IDeptUserService;
import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 机构Service
 */
@Service
public class DeptInfoServiceImpl extends BaseTreeServiceImpl<DeptInfoRepository, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {

    @Autowired
    private IDeptUserService iDeptUserService;

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        List<DeptInfo> children = iBaseRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and(root.<String>get("parentId").in(ids));
            }
        });
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException("所选数据下面含有子元素集合，不能删除！需要先删除子元素");
        }
        if(CollectionUtil.isNotEmpty(iDeptUserService.findSecondIdsByFirstIds(ids))){
            throw new SysException("所选部门下有用户，不能删除！请先删除所选部门下所有用户");
        }
    }

    @Override
    public DeptInfoDTO findById(String id) throws BaseException {
        DeptInfoDTO deptInfoDTO = super.findById(id);
        if(StringUtils.isNotBlank(deptInfoDTO.getParentId())){
            DeptInfoDTO parentDTO = findById(deptInfoDTO.getParentId());
            deptInfoDTO.setParentName(parentDTO.getDeptName());
        }else{
            deptInfoDTO.setParentName("没有上级部门");
        }
        return deptInfoDTO;
    }

    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> all = iBaseRepository.findAll();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(all);
        return deptInfoDTOS;
    }
}