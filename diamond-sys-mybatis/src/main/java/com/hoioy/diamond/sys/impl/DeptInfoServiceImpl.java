package com.hoioy.diamond.sys.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.DeptInfoMapper;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import com.hoioy.diamond.sys.service.IDeptUserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeptInfoServiceImpl extends BaseTreeServiceImpl<DeptInfoMapper, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {
    @Autowired
    private IDeptUserService iDeptUserService;

    @Override
    public PageDTO<DeptInfoDTO> getPage(PageDTO<DeptInfoDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        DeptInfo domain = getDomainFilterFromPageDTO(pageDTO);
        IPage<Map> domainIPage = iBaseRepository.selectPage(page, domain);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(domainIPage, DeptInfoDTO.class,pageDTO);
        return resultPage;
    }

    @Override
    public DeptInfoDTO findById(String id) throws BaseException {
        DeptInfoDTO deptInfoDTO = super.findById(id);
        if (StrUtil.isNotBlank(deptInfoDTO.getParentId())) {
            DeptInfoDTO parentDTO = findById(deptInfoDTO.getParentId());
            deptInfoDTO.setParentName(parentDTO.getDeptName());
        } else {
            deptInfoDTO.setParentName("没有上级部门");
        }
        return deptInfoDTO;
    }
    @Override
    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> list = iBaseRepository.findAllSort();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(list);
        return deptInfoDTOS;
    }

    @Override
    public Boolean beforeBatchRemove(List<String> ids) {
        super.beforeBatchRemove(ids);
        QueryWrapper<DeptInfo> ew = new QueryWrapper<>();
        ew.in("parent_id", ids);
        List<DeptInfo> children = iBaseRepository.selectList(ew);
        if (CollectionUtil.isNotEmpty(children)) {
            throw new SysException(messageSource.getMessage("exception.hasChild", null,  LocaleContextHolder.getLocale()));
        }

        if (CollectionUtil.isNotEmpty(iDeptUserService.findSecondIdsByFirstIds(ids))) {
            throw new SysException(messageSource.getMessage("exception.containUser", null,  LocaleContextHolder.getLocale()));
        }

        return true;
    }

}
