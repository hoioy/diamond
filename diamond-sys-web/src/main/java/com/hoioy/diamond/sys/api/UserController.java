package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.ResultDTO;
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

import java.io.IOException;
import java.util.Optional;

@RestController
@Api(tags = {"02.用户操作接口"})
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserInfoService, UserInfoDTO> {

    @Autowired
    private IFileStorageService storageService;

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
        UserInfoDTO dto = (UserInfoDTO) iBaseService.findById(userId).get();
        return new ResultDTO(dto);
    }

    /**
     * @Description: 执行用户头像上传
     */
    @ApiOperation(value = "执行用户头像上传")
    @PostMapping(value = "/user-upload-avatar-rest")
    public ResultDTO avatarUploadRest(@RequestParam("file") MultipartFile file, @RequestParam("id") String id) throws
            IOException {
        String loginName = null;
        if (StringUtils.isEmpty(id)) {
            throw new SysException("用户ID不能为空!");
        }
        try {
            Optional<UserInfoDTO> userInfoDTO = iBaseService.findById(id);
            loginName = userInfoDTO.get().getLoginName();
            if (StringUtils.isEmpty(loginName)) {
                throw new SysException("获取用户信息失败");
            }
        } catch (Exception e) {
            log.error("获取用户失败:{}", e);
            throw new SysException("获取用户信息失败!");
        }

        if (file.isEmpty()) {
            throw new SysException("上传文件不能为空");
        }

        log.info("avatarUploadRest_fileName{}", file.getBytes().length);
        if (file.getBytes().length > 1024 * 1024 * 2) {
            throw new SysException("图片大小不可超过2M!");
        }
        //保存到指定目录,并返回文件名称  需要修改
        String newName = this.storageService.saveFile(file, "useravatar/", "user_" + loginName).getFileName();
        //保存到数据库
        this.iBaseService.saveUserAvatar(loginName, newName, file);
        return new ResultDTO("头像上传成功!");
    }
}