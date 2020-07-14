package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.sys.dto.FileStorageDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.IFileStorageService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@RestController
@Api(tags = {"02.用户操作接口"})
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserInfoService, UserInfoDTO> {

    @Autowired
    private IFileStorageService storageService;

    @Valid
    @ApiOperation(value = "用户、角色、部门多表分页查询。")
    @PostMapping(value = "/composite-search")
    public ResultDTO<PageDTO<UserInfoDTO>> compositeSelectByPage(@RequestBody PageDTO<UserInfoDTO> pageDTO) {
        pageDTO = iBaseService.getUserOnlyByRoleIdOrDeptIdPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    @ApiOperation(value = "根据loginName查询用户Id")
    @GetMapping(value = "/findIdByLoginName")
    public ResultDTO<String> findIdByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        return new ResultDTO(userId);
    }

    @ApiOperation(value = "根据loginName查询用户信息")
    @GetMapping(value = "/findByLoginName")
    public ResultDTO<UserInfoDTO> findByLoginName(String loginName) {
        String userId = iBaseService.findIdByLoginName(loginName);
        UserInfoDTO dto = iBaseService.findWithPasswordById(userId);
        return new ResultDTO(dto);
    }

    /**
     * @Description: 执行用户头像上传
     */
    @ApiOperation(value = "执行用户头像上传")
    @PostMapping(value = "/user-upload-avatar-rest")
    public ResultDTO avatarUploadRest(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws IOException {
        if (StringUtils.isBlank(id)) {
            throw new SysException("用户ID不能为空!");
        }
        if (file.isEmpty()) {
            throw new SysException("上传文件不能为空");
        }

        log.info("avatarUploadRest_fileName{}", file.getBytes().length);
        if (file.getBytes().length > 1024 * 1024 * 2) {
            throw new SysException("图片大小不可超过2M!");
        }
        FileStorageDTO fileStorageDTO = storageService.saveFile(file, FileStorageController.secondLevelImgDirectory+ File.separator,
                file.getOriginalFilename());

        UserInfoDTO dto = new UserInfoDTO();
        dto.setAvatar(FileStorageController.secondLevelImgUrl+"/"+fileStorageDTO.getFileName());
        dto.setId(id);
        iBaseService.update(dto);
        return new ResultDTO(dto.getAvatar());
    }
}