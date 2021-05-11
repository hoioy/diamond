package com.hoioy.sample.mapper;

import com.hoioy.diamond.common.base.IBaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Course;
import org.apache.ibatis.annotations.Param;

public interface CourseMapper extends IBaseMapper<Course> {

    /**
     *  分页
     */
    IPage<Course> selectPage(@Param("page") Page page, @Param("course") Course course);

}
