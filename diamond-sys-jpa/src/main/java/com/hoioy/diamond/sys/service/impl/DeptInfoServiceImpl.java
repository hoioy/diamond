package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.sys.domain.*;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IDeptInfoService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 机构Service
 */
@Service
public class DeptInfoServiceImpl extends BaseServiceImpl<DeptInfoRepository, DeptInfo, DeptInfoDTO> implements IDeptInfoService<DeptInfo> {
    /**
     * 删除部门
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeById(String id) throws BaseException {
        if (StrUtil.isBlank(id)) {
            throw new SysException("id不能空");
        }
        List<DeptInfo> children = iBaseRepository.findByParentId(id);
        if (!CollectionUtils.isEmpty(children)) {
            throw new SysException("该条记录下面含有子元素集合，不能删除！");
        }
        return super.removeById(id);
    }

    public List<DeptInfoDTO> findAll() {
        List<DeptInfo> all = iBaseRepository.findAll();
        List<DeptInfoDTO> deptInfoDTOS = domainListToDTOList(all);
        return deptInfoDTOS;
    }
}