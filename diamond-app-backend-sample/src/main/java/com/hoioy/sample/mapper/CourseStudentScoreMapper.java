package com.hoioy.sample.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.CourseStudentScore;
import com.hoioy.sample.dto.ScoreWithNamesDTO;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface CourseStudentScoreMapper extends IBaseJoinMapper<CourseStudentScore> {

    /**
     *  分页
     */
    IPage<CourseStudentScore> selectPage(@Param("page") Page page, @Param("courseStudentScore") CourseStudentScore courseStudentScore);
    IPage<Map> findScoreWithNamesPageable(@Param("page") Page page, @Param("scoreWithNamesDTO") ScoreWithNamesDTO scoreWithNamesDTO);
}
