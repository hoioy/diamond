package com.hoioy.diamond.sys.domain;


import com.hoioy.diamond.common.base.IBaseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类名称：DeptInfoRepository 机构单位Repository
 */
@Repository
public interface DeptInfoRepository extends IBaseRepository<DeptInfo, String> {

    List<DeptInfo> findByParentId(String parentId);

    /**
     * 查询树根
     */
    List<DeptInfo> findByDeptStateAndParentIdIsNullOrderByDeptIndexAsc(@Param("deptState") String deptState);
}
