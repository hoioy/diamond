package com.hoioy.sample.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherMapper extends BaseMapper<Teacher> {

    /**
     * 分页
     */
    IPage<Teacher> selectPage(@Param("page") Page page, @Param("teacher") Teacher teacher);
}
