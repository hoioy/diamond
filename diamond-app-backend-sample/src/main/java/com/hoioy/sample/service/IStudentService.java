package com.hoioy.sample.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.sample.dto.StudentDTO;

import java.util.List;

public interface IStudentService<D extends CommonDomain> extends IBaseService<StudentDTO, D> {

    List<StudentDTO> findByStudentClass(String studentClass);

    String dynamicSql();
}
