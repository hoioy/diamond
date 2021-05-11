package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.sys.dto.FileStorageDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IFileStorageService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import com.hoioy.diamond.sys.web.BaseWebMvcConfigurer;
import cn.hutool.core.util.StrUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = {"02.用户操作接口"})
@ApiSupport(order = 2)
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserInfoService, UserInfoDTO> {

    @Autowired
    private IFileStorageService storageService;

    @ApiOperation(value = "8.用户、角色、部门多表分页查询。")
    @ApiOperationSupport(order = 8)
    @PostMapping(value = "/composite-search")
    public ResultDTO<PageDTO<UserInfoDTO>> compositeSelectByPage(@Valid @RequestBody PageDTO<UserInfoDTO> pageDTO) {
        pageDTO = iBaseService.getUserOnlyByRoleIdOrDeptIdPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    @ApiOperation(value = "9.根据loginName查询用户Id")
    @ApiOperationSupport(order = 9)
    @GetMapping(value = "/findIdByLoginName")
    public ResultDTO<String> findIdByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        return new ResultDTO(userId);
    }

    @ApiOperation(value = "10.根据loginName查询用户信息")
    @ApiOperationSupport(order = 10)
    @GetMapping(value = "/findByLoginName")
    public ResultDTO<UserInfoDTO> findByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        UserInfoDTO dto = iBaseService.findWithPasswordById(userId);
        return new ResultDTO(dto);
    }

    @ApiOperation(value = "11.执行用户头像上传")
    @ApiOperationSupport(order = 11)
    @PostMapping(value = "/user-upload-avatar-rest")
    public ResultDTO avatarUploadRest(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws IOException {
        if (StrUtil.isBlank(id)) {
            throw new SysException("用户ID不能为空!");
        }
        if (file.isEmpty()) {
            throw new SysException("上传文件不能为空");
        }

        log.info("avatarUploadRest_fileName{}", file.getBytes().length);
        if (file.getBytes().length > 1024 * 1024 * 2) {
            throw new SysException("图片大小不可超过2M!");
        }
        FileStorageDTO fileStorageDTO = storageService.saveFile(file, BaseWebMvcConfigurer.secondLevelImgDirectory + File.separator,
                file.getOriginalFilename());

        UserInfoDTO dto = new UserInfoDTO();
        dto.setAvatar(BaseWebMvcConfigurer.secondLevelImgUrl + "/" + fileStorageDTO.getFileName());
        dto.setId(id);
        iBaseService.update(dto);
        return new ResultDTO(dto.getAvatar());
    }

    @ApiOperation(value = "12.查询用户是否存在")
    @ApiOperationSupport(order = 12)
    @GetMapping(value = "/checkUserExist")
    public ResultDTO<Boolean> checkUserExist(String loginName) {
        Boolean exist = iBaseService.checkUserExist(loginName);
        return new ResultDTO(exist);
    }
}