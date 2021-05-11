package com.hoioy.sample.service;

import com.hoioy.diamond.common.domain.CommonDomain;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.service.IBaseJoinService;
import com.hoioy.sample.dto.CourseDTO;
import com.hoioy.sample.dto.CourseStudentScoreDTO;
import com.hoioy.sample.dto.ScoreWithNamesDTO;
import com.hoioy.sample.dto.StudentDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 太极计算机股份有限公司
 * @since 2020-04-07
 */
public interface ICourseStudentScoreService<D extends CommonDomain> extends IBaseJoinService<CourseStudentScoreDTO, D> {
    List<CourseDTO> getCourseDTOsByStudentId(String studentId);

    List<StudentDTO> getStudengDTOsByStudentId(String courseId);

    List<String> getCourseIdsByStudentId(String studentId);

    List<String> getCourseIdsByStudentIds(List<String> studentIds);

    List<String> getStudentIdsByCourseId(String courseId);

    List<String> getStudentIdsByCourseIds(List<String> courseIds);

    PageDTO<ScoreWithNamesDTO> findScoreWithNamesPageable(PageDTO<ScoreWithNamesDTO> pageDTO);
}
