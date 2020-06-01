package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseTreeMapper;
import com.hoioy.diamond.sys.domain.DeptInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface DeptInfoMapper extends IBaseTreeMapper<DeptInfo> {

    List<DeptInfo> findAll(@Param("deptInfo") DeptInfo deptInfo);

    DeptInfo findByDeptName(String deptName);

    List<DeptInfo> findAllSort();
}
