package com.hoioy.sample.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.sample.dto.CourseDTO;
import com.hoioy.sample.service.ICourseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "202.课程操作接口")
@RestController
@RequestMapping("/sample/course")
public class CourseController extends BaseController<ICourseService, CourseDTO> {

}

