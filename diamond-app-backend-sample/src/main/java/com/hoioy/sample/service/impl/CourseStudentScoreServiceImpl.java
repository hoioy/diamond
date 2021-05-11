package com.hoioy.sample.service.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.CourseStudentScore;
import com.hoioy.sample.dto.CourseDTO;
import com.hoioy.sample.dto.CourseStudentScoreDTO;
import com.hoioy.sample.dto.ScoreWithNamesDTO;
import com.hoioy.sample.dto.StudentDTO;
import com.hoioy.sample.mapper.CourseStudentScoreMapper;
import com.hoioy.sample.service.ICourseService;
import com.hoioy.sample.service.ICourseStudentScoreService;
import com.hoioy.sample.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseStudentScoreServiceImpl extends BaseJoinServiceImpl<CourseStudentScoreMapper, CourseStudentScore, CourseStudentScoreDTO> implements ICourseStudentScoreService<CourseStudentScore> {
    @Autowired
    private ICourseService iCourseService;

    @Autowired
    private IStudentService iStudentService;

    @Override
    public List<CourseDTO> getCourseDTOsByStudentId(String studentId) {
        List<String> courseIds = getCourseIdsByStudentId(studentId);
        return iCourseService.findByIds(courseIds);
    }

    @Override
    public List<StudentDTO> getStudengDTOsByStudentId(String courseId) {
        List<String> studentIds = getStudentIdsByCourseId(courseId);
        return iStudentService.findByIds(studentIds);
    }

    @Override
    public List<String> getCourseIdsByStudentId(String studentId) {
        return super.findSecondIdsByFirstId(studentId);
    }

    @Override
    public List<String> getCourseIdsByStudentIds(List<String> studentIds) {
        return super.findSecondIdsByFirstIds(studentIds);
    }

    @Override
    public List<String> getStudentIdsByCourseId(String courseId) {
        return super.findSecondIdsByFirstId(courseId);
    }

    @Override
    public List<String> getStudentIdsByCourseIds(List<String> courseIds) {
        return super.findFirstIdsBySecondIds(courseIds);
    }

    @Override
    public PageDTO<ScoreWithNamesDTO> findScoreWithNamesPageable(PageDTO<ScoreWithNamesDTO> pageDTO) {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        ScoreWithNamesDTO scoreWithNamesDTO = pageDTO.getFilters();
        IPage<Map> pageList = iBaseRepository.findScoreWithNamesPageable(page, scoreWithNamesDTO);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(pageList, ScoreWithNamesDTO.class);
        return resultPage;
    }

}
