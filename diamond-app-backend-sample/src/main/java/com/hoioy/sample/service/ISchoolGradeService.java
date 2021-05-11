package com.hoioy.sample.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseTreeService;
import com.hoioy.sample.dto.SchoolGradeDTO;

import java.util.List;

public interface ISchoolGradeService<D extends CommonDomain> extends IBaseTreeService<SchoolGradeDTO, D> {
    List<SchoolGradeDTO> findTree();
}
