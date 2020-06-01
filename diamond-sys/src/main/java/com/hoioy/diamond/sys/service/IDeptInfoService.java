package com.hoioy.diamond.sys.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.diamond.sys.dto.DeptInfoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构Service
 */
@Service
public interface IDeptInfoService<D extends CommonDomain> extends IBaseTreeService<DeptInfoDTO, D> {

    List<DeptInfoDTO> findAll();
}