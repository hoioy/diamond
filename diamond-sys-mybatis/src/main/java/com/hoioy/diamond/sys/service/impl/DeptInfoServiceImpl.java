package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.DeptInfoMapper;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import com.hoioy.diamond.sys.service.IDeptUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptInfoServiceImpl extends BaseTreeServiceImpl<DeptInfoMapper, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {
    @Autowired
    private IDeptUserService iDeptUserService;

    @Override
    public PageDTO<DeptInfoDTO> getPage(PageDTO<DeptInfoDTO> pageDTO) {
        DeptInfo bean = getDomainFilterFromPageDTO(pageDTO);
        List<DeptInfo> all = iBaseRepository.findAll(bean);
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(all);

        if (CollUtil.isNotEmpty(deptInfoDTOS)) {
            pageDTO.setList(deptInfoDTOS);
            pageDTO.setTotal((long) deptInfoDTOS.size());
            return pageDTO;
        } else {
            return pageDTO;
        }
    }

    @Override
    public void beforeRemove(List<String> ids) {
        super.beforeRemove(ids);
        QueryWrapper<DeptInfo> ew = new QueryWrapper<>();
        ew.in("parent_id", ids);
        List<DeptInfo> children = iBaseRepository.selectList(ew);
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException(messageSource.getMessage("exception.hasChild", null,  LocaleContextHolder.getLocale()));
        }

        if (CollectionUtil.isNotEmpty(iDeptUserService.findSecondIdsByFirstIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null,  LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public DeptInfoDTO findById(String id) throws BaseException {
        DeptInfoDTO deptInfoDTO = super.findById(id);
        DeptInfoDTO parentDTO = findById(deptInfoDTO.getParentId());
        deptInfoDTO.setParentName(parentDTO.getDeptName());
        return deptInfoDTO;
    }
    @Override
    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> list = iBaseRepository.findAllSort();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(list);
        return deptInfoDTOS;
    }
}
