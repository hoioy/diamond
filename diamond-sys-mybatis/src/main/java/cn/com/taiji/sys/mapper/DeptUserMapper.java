package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.DeptUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
