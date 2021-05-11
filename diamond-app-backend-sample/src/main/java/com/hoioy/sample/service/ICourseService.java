package com.hoioy.sample.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.service.IBaseService;
import com.hoioy.sample.dto.CourseDTO;

public interface ICourseService<D extends CommonDomain> extends IBaseService<CourseDTO, D> {

}
