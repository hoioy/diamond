package com.hoioy.sample.mapper;

import com.hoioy.diamond.common.base.IBaseTreeMapper;
import com.hoioy.sample.domain.SchoolGrade;

import java.util.List;

public interface SchoolGradeMapper extends IBaseTreeMapper<SchoolGrade> {

    List<SchoolGrade> selectAll();
}
