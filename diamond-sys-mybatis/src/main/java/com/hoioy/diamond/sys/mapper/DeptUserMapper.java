package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.sys.domain.DeptUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface DeptUserMapper extends IBaseJoinMapper<DeptUser> {

    List<String> findUserIdsByDeptIds(@Param("deptIds") List<String> deptIds);
    List<String> findDeptIdsByUserIds(@Param("userIds") List<String> userIds);
}
