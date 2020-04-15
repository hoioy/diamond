package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.mapper.DeptInfoMapper;
import com.hoioy.diamond.sys.service.*;
import com.hoioy.diamond.common.util.DiamondMybatisPageUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Service
public class DeptInfoServiceImpl extends BaseServiceImpl<DeptInfoMapper, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {
    @Override
    public PageDTO getPage(PageDTO pageDTO) {
        DeptInfo bean = DiamondMybatisPageUtil.getBean(pageDTO, DeptInfo.class);
        List<DeptInfo> all = baseMapper.findAll(bean);
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
    public String save(DeptInfoDTO dto) throws BaseException {
        DeptInfo deptInfo = baseMapper.findByDeptName(dto.getDeptName());
        if (deptInfo != null) {
            throw new SysException("该部门名称已存在");
        }
//        dto.setCreatedDate(LocalDateTime.now());
        return super.save(dto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(DeptInfoDTO dto) throws BaseException {
        DeptInfo deptInfo = baseMapper.findByDeptName(dto.getDeptName());
        if (deptInfo != null&&!dto.getId().equals(deptInfo.getId())) {
            throw new SysException("该部门名称已存在");
        }
        return super.update(dto);
    }

    @Override
    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> list = baseMapper.findAllSort();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(list);
        return deptInfoDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        if (StrUtil.isNotBlank(id)) {
            List<DeptInfo> byParentId = this.baseMapper.findByParentId(id);
            if (CollUtil.isNotEmpty(byParentId)) {
                throw new SysException("该条记录下面含有子元素集合，不能删除！");
            }
            return super.removeById(id);
        } else {
            return false;
        }
    }
}
