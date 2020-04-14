package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.hoioy.diamond.sys.domain.ParameterInfo;
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
public interface ParameterInfoMapper extends IBaseMapper<ParameterInfo> {

    /**
     *  分页
     */
    IPage<ParameterInfo> selectPage(@Param("page") Page page, @Param("parameterInfo") ParameterInfo parameterInfo);

    List<String> findIdsByParameterKeys(@Param("parameterKeys") List<String> parameterKeys);
}
