package com.hoioy.sample.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.sample.dto.StudentDTO;
import com.hoioy.sample.service.IStudentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "201.学生操作接口")
@RestController
@RequestMapping("/sample/student")
public class StudentController extends BaseController<IStudentService, StudentDTO> {

    @ApiOperation(value = "根据studentClass查询")
    @GetMapping(value = "/findByStudentClass")
    public ResultDTO findByStudentClass(@RequestParam String studentClass) {
        log.debug("into findByStudentClass");
        List<StudentDTO> data = iBaseService.findByStudentClass(studentClass);
        return new ResultDTO(data);
    }

    @ApiOperation(value = "动态sql示例")
    @GetMapping(value = "/dynamicsql")
    public ResultDTO dynamicSql() {
        String s = iBaseService.dynamicSql();
        return new ResultDTO(s);
    }
}

