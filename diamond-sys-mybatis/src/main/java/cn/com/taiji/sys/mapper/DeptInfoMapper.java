package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.DeptInfo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
public interface DeptInfoMapper extends IBaseMapper<DeptInfo> {

    /**
     * 根据条件查询数据
     * @param bean
     * @return
     */
    List<DeptInfo> findAll(@Param("deptInfo") DeptInfo deptInfo);

    DeptInfo findByDeptName(String deptName);

    List<DeptInfo> findByParentId(String parentId);

    List<DeptInfo> findAllSort();
}
