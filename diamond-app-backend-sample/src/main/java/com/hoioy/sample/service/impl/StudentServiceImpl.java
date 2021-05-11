package com.hoioy.sample.service.impl;

import com.hoioy.diamond.common.base.BaseServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.CommonMybatisPageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hoioy.sample.domain.Student;
import com.hoioy.sample.dto.StudentDTO;
import com.hoioy.sample.exception.CustomSampleException;
import com.hoioy.sample.mapper.StudentMapper;
import com.hoioy.sample.service.IStudentService;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends BaseServiceImpl<StudentMapper, Student, StudentDTO> implements IStudentService<Student> {

    @Override
    public PageDTO getPage(PageDTO pageDTO) throws BaseException {
        Page page = CommonMybatisPageUtil.getInstance().pageDTOtoPage(pageDTO);
        Student student = getDomainFilterFromPageDTO(pageDTO);
        IPage<Student> studentIPage = iBaseRepository.selectPage(page, student);
        PageDTO resultPage = CommonMybatisPageUtil.getInstance().iPageToPageDTO(studentIPage, StudentDTO.class);
        return resultPage;
    }

    @Override
    public StudentDTO beforeCreate(StudentDTO dto) {
        if (StrUtil.isEmpty(dto.getStudentClass())) {
            throw new CustomSampleException("学生班级不能为空");
        }
        if (StrUtil.isEmpty(dto.getStudentName())) {
            throw new CustomSampleException("学生姓名不能为空");
        }
        if (StrUtil.isEmpty(dto.getStudentSex())) {
            throw new CustomSampleException("学生性别不能为空");
        }
        return super.beforeCreate(dto);
    }


    @Override
    public List<StudentDTO> findByStudentClass(String studentClass) {
        List<Student> students = iBaseRepository.findByStudentClass(studentClass);
        return domainListToDTOList(students);
    }

    @Override
    public String dynamicSql() {
        String s = iBaseRepository.selectTime();
        return s;
    }
}
