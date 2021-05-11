package com.hoioy.sample.service.impl;

import com.hoioy.diamond.common.base.BaseTreeServiceImpl;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.sample.domain.SchoolGrade;
import com.hoioy.sample.dto.SchoolGradeDTO;
import com.hoioy.sample.mapper.SchoolGradeMapper;
import com.hoioy.sample.service.ISchoolGradeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 学校年纪组织架构
 */
@Service
public class SchoolGradeServiceImpl extends BaseTreeServiceImpl<SchoolGradeMapper, SchoolGrade, SchoolGradeDTO> implements ISchoolGradeService<SchoolGrade> {

    @Override
    public PageDTO<SchoolGradeDTO> getPage(PageDTO<SchoolGradeDTO> pageDTO) {
        return null;
    }

    @Override
    public List<SchoolGradeDTO> findTree() {
        List<SchoolGrade> all = iBaseRepository.selectAll();
        List<SchoolGradeDTO> gradeDTOS = this.domainListToDTOList(all);
        List<SchoolGradeDTO> gradeDTOS1 = this.listToTree(gradeDTOS, null);
        return gradeDTOS1;
    }
}
