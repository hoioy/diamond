package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.DeptInfoMapper;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeptInfoServiceImpl extends BaseTreeServiceImpl<DeptInfoMapper, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {
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
    @Transactional(rollbackFor = Exception.class)
    public DeptInfoDTO save(DeptInfoDTO dto) throws BaseException {
        DeptInfo deptInfo = iBaseRepository.findByDeptName(dto.getDeptName());
        if (deptInfo != null) {
            throw new SysException("该部门名称已存在");
        }
//        dto.setCreatedDate(LocalDateTime.now());
        return super.save(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DeptInfoDTO update(DeptInfoDTO dto) throws BaseException {
        DeptInfo deptInfo = iBaseRepository.findByDeptName(dto.getDeptName());
        if (deptInfo != null && !dto.getId().equals(deptInfo.getId())) {
            throw new SysException("该部门名称已存在");
        }
        return super.update(dto);
    }

    @Override
    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> list = iBaseRepository.findAllSort();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(list);
        return deptInfoDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        if (StrUtil.isNotBlank(id)) {
            List<DeptInfoDTO> byParentId = findByParentId(id);
            if (CollUtil.isNotEmpty(byParentId)) {
                throw new SysException("该条记录下面含有子元素集合，不能删除！");
            }
            return super.removeById(id);
        } else {
            return false;
        }
    }
}
