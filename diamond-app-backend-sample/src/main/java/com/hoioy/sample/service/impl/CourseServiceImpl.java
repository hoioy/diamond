package com.hoioy.sample.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Course;
import com.hoioy.sample.dto.CourseDTO;
import com.hoioy.sample.mapper.CourseMapper;
import com.hoioy.sample.service.ICourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends BaseServiceImpl<CourseMapper, Course, CourseDTO> implements ICourseService<Course> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Course course =getDomainFilterFromPageDTO(pageDTO);
        IPage<Course> courseIPage = iBaseRepository.selectPage(page, course);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(courseIPage, CourseDTO.class);
        return resultPage;
    }

}
