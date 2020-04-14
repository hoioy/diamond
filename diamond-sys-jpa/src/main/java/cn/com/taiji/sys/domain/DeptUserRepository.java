package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseJoinRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 机构用户关系表Repository
 */
@Repository
public interface DeptUserRepository extends IBaseJoinRepository<DeptUser, String> {

    @Query("select k.userId from DeptUser k where k.deptId in (:deptIds)")
    List<String> findUserIdsByDeptIds(@Param("deptIds") List<String> deptIds);


    @Query("select k.deptId from DeptUser k where k.userId in (:userIds)")
    List<String> findDeptIdsByUserIds(@Param("userIds") List<String> userIds);
}
