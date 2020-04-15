package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.DiamondDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构Service
 */
@Service
public interface IDeptInfoService<D extends DiamondDomain> extends IBaseService<DeptInfoDTO, D> {

    List<DeptInfoDTO> findAll();
}