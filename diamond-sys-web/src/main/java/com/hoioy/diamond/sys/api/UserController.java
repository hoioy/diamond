package com.hoioy.diamond.sys.api;


import com.hoioy.diamond.common.api.BaseController;
import com.hoioy.diamond.common.dto.PageDTO;
import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.exception.BaseException;
import com.hoioy.diamond.common.util.TDFSecurityUtils;
import com.hoioy.diamond.sys.dto.*;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

@RestController
@Api(tags = {"02.用户操作接口"})
@RequestMapping("/system/user")
public class UserController extends BaseController<IUserInfoService, UserInfoDTO> {
    @Autowired
    private IDeptInfoService iDeptInfoService;

    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private IRoleUserService iRoleUserService;
    @Autowired
    private IDeptUserService iDeptUserService;
    @Autowired
    private IUserGroupUserService iUserGroupUserService;

    @Autowired
    private IUserGroupService iUserGroupService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IFileStorageService storageService;


    /**
     * @Description:根据id查询用户
     */
    @ApiOperation("001.根据id查询用户信息")
    @GetMapping(value = "/query-by-id")
    public ResultDTO findByIds(@RequestParam(value = "id", required = false) String id) {
        log.info("查询用户信息列表入参：{}", id);
        Optional<UserInfoDTO> userDTO = iBaseService.findById(id);
        return new ResultDTO(userDTO.get());
    }

    /**
     * @Description:用户分页查询
     * @Param: [models]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "002.分页查询用户信息列表")
    @PostMapping(value = "/query")
    @Valid
    public ResultDTO userListQuery(@RequestBody PageDTO pageDTO) {
        log.info("userListQuery：{}", pageDTO);
        pageDTO = iBaseService.getPage(pageDTO);
        return new ResultDTO(pageDTO);
    }

    /**
     * @Description:添加用户
     * @Param: [userInfoDto, result]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "执行用户添加")
    @PostMapping(value = "/add")
    @Valid
    public ResultDTO userAddRest(HttpServletRequest request , @RequestBody UserInfoDTO userInfoDto) throws BaseException {
        UserInfoDTO userInfoDtoFromDatabase = iBaseService.findByLoginName(userInfoDto.getLoginName());
        if (null != userInfoDtoFromDatabase) {
            throw new SysException("用户已经存在");
        }
        userInfoDto.setFlag(1);
        String userId = iBaseService.save(userInfoDto);
        UserInfoDTO userInfoDTO = (UserInfoDTO) iBaseService.findById(userId).get();
        this.iBaseService.savePwd(userInfoDTO.getId(), userInfoDto.getPassword());

        return new ResultDTO(userInfoDTO);
    }

    /**
     * @Description:添加用户
     * @Param: [userInfoDto, result]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "执行用户编辑")
    @PostMapping(value = "/edit")
    public ResultDTO userEdit(@RequestBody UserInfoDTO userInfoDto) {
        String userId = iBaseService.update(userInfoDto);
        Optional<UserInfoDTO> userDTO = iBaseService.findById(userId);
        return new ResultDTO(userDTO.get());
    }

    /**
     * @Description:用户删除
     * @Param: [id]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "执行用户删除")
    @PostMapping(value = "/del")
    public ResultDTO userDeleteRest(@RequestBody List<UserInfoDTO> userInfoDto) {
        List<String> ids = new ArrayList<>();
        userInfoDto.forEach(userDTO -> {
            ids.add(userDTO.getId());
        });
        Boolean success = iBaseService.removeByIds(ids);
        return new ResultDTO(success);
    }

    /**
     * @return ResultDTO
     * @Author zhoujial
     * 执行用户密码设置
     * @Date 上午10:15 18-12-28
     * @Param [models]
     **/
    @ApiOperation(value = "执行用户密码设置")
    @PostMapping(value = "/user-pwd-save-rest")
    public ResultDTO userPwdToSave(@RequestBody String models) throws IOException {
        Map<String, String> idAndPassword = new HashMap<>();

        if (models != null && models.length() > 0) {
            idAndPassword = objectMapper.readValue(models, new TypeReference<Map>() {
            });
        }
        this.iBaseService.savePwd(idAndPassword.get("id"), idAndPassword.get("password"));

        return new ResultDTO("成功");
    }

    /**
     * @Description:查询所有用户
     * @Param: [models]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "查询用户role信息列表")
    @GetMapping(value = "/all-roles")
    public ResultDTO userRoleListQuery(@RequestParam(value = "id", required = false) String id) {
        UserInfoDTO userInfoDTO = (UserInfoDTO) iBaseService.findById(id).get();
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userInfoDTO.getId()));
        List<RoleDTO> roleDTOS = iRoleService.findByIds(roleIds);
        return new ResultDTO(roleDTOS);
    }

    /**
     * @Description:用户添加角色
     * @Param: [models]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "用户添加角色")
    @PostMapping("/add-role")
    public ResultDTO roleUserSaveOne(@ApiParam(name = "id", value = "用户id", required = false) @RequestBody String
                                             models) throws IOException {

        List<Map> pageDTO = objectMapper.readValue(models, new TypeReference<List<Map>>() {
        });
        String[] userids = new String[pageDTO.size()];
        for (int i = 0; i < pageDTO.size(); i++) {
            userids[i] = (String) pageDTO.get(i).get("userId");
        }
        for (int i = 0; i < pageDTO.size(); i++) {
            this.iRoleUserService.saveRoleUsers(userids, "", (String) pageDTO.get(i).get("roleId"));
        }

        return new ResultDTO("");
    }

    /**
     * @Description:删除角色信息
     * @Param: [models]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @ApiOperation(value = "删除用户角色信息")
    @PostMapping(value = "/del-role")
    public ResultDTO delUserRole(@RequestBody String models) {
        RoleUserJoinDTO roleUserPK = new RoleUserJoinDTO();
        if (models != null && models.length() > 0) {
            try {
                roleUserPK = objectMapper.readValue(models, new TypeReference<RoleUserJoinDTO>() {
                });
            } catch (JsonParseException e) {
                log.error("delUserRole_JsonParseException{}:", e);
            } catch (IOException e) {
                log.error("delUserRole_IOException{}:", e);
            }
        }

        iRoleUserService.remove(roleUserPK);
        return new ResultDTO(null);

    }


    /**
     * 查询用户具有的所有部门信息
     */
    @ApiOperation(value = "查询用户全部部门信息")
    @GetMapping(value = "/all-depts")
    public ResultDTO queryAllUserDepts(@RequestParam(value = "id", required = false) String id) {
        List<String> deptIds = iDeptUserService.findDeptIdsByUserIds(Arrays.asList(id));
        List<DeptInfoDTO> list = iDeptInfoService.findByIds(deptIds);
        return new ResultDTO(list);
    }

    /**
     * @return 返回值ResultDTO
     * @throws @param userId
     *                删除用户部门信息，告知前端结果
     * @Author wanghw
     * @CreatDate 2018年11月8日
     * @UpdateUser 方法更新者
     * @UpdateDate 2018年11月8日
     */
    @ApiOperation(value = "删除用户部门信息")
    @PostMapping(value = "/del-dept")
    @Valid
    public ResultDTO delUserDept(@RequestBody DeptUserJoinDTO deptUserPK) {
        iDeptUserService.remove(deptUserPK);
        return new ResultDTO(null);
    }

    /**
     * 增加用户部门信息，告知前端结果
     */
    @ApiOperation(value = "添加用户部门信息")
    @PostMapping(value = "/add-dept")
    public ResultDTO addUserDept(@RequestBody String models) throws IOException {
        List<DeptUserJoinDTO> deptUserPKList = new ArrayList();
        if (models != null && models.length() > 0) {
            deptUserPKList = objectMapper.readValue(models, new TypeReference<List<DeptUserJoinDTO>>() {
            });
        }

        iDeptUserService.batchSave(deptUserPKList);
        return new ResultDTO(null);

    }

    /**
     * 查询用户具有的所有用户组信息
     */
    @ApiOperation(value = "查询用户组信息")
    @GetMapping(value = "/all-groups")
    public ResultDTO queryAllUserGroups(@RequestParam(value = "id", required = false) String id) {
        List<String> groupIds = iUserGroupUserService.findGroupIdsByUserIds(Arrays.asList(id));
        List<UserGroupDTO> list = iUserGroupService.findByIds(groupIds);
        return new ResultDTO(list);

    }

    /**
     * 删除用户组信息，告知前端结果
     */
    @ApiOperation(value = "删除用户组信息")
    @PostMapping(value = "/del-group")
    public ResultDTO delUserGroup(@RequestBody String models) throws IOException {
        UserGroupUserJoinDTO userGroupUserPK = new UserGroupUserJoinDTO();
        if (models != null && models.length() > 0) {
            userGroupUserPK = objectMapper.readValue(models, new TypeReference<UserGroupUserJoinDTO>() {
            });
        }

        iUserGroupUserService.remove(userGroupUserPK);
        return new ResultDTO(null);

    }

    /**
     * 增加用户 用户组信息，告知前端结果
     */
    @ApiOperation(value = "添加用户组信息")
    @PostMapping(value = "/add-group")
    public ResultDTO addUserGroup(@RequestBody String models) {
        List<UserGroupUserJoinDTO> userGroupUserPK = new ArrayList();
        if (models != null && models.length() > 0) {
            try {
                userGroupUserPK = objectMapper.readValue(models, new TypeReference<List<UserGroupUserJoinDTO>>() {
                });
            } catch (JsonParseException e) {
                log.error("addUserGroup_JsonParseException{}:", e);
            } catch (IOException e) {
                log.error("addUserGroup_IOException{}:", e);
            }
        }

        iUserGroupUserService.batchSave(userGroupUserPK);
        return new ResultDTO(null);

    }


    /**
     * @Description:检查登录用户名
     */
    @ApiOperation(value = "检查登录用户信息")
    @PostMapping(value = "/check-login-name")
    public ResultDTO checkLoginName(@RequestBody String models) throws IOException {
        UserInfoDTO user = null;
        Map userForCheck = new HashMap();

        userForCheck = objectMapper.readValue(models, new TypeReference<Map>() {
        });
        user = iBaseService.findByLoginName((String) userForCheck.get("loginName"));
        if (null == user) {
            Map existMap = new HashMap();
            existMap.put("exist", 0);
            return new ResultDTO(existMap);
        } else {
            Map existMap = new HashMap();
            existMap.put("exist", 1);
            return new ResultDTO(existMap);
        }

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
            Optional<UserInfoDTO> userDto = iBaseService.findById(id);
            loginName = userDto.get().getLoginName();
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
        String newName = this.storageService.saveFile(file, "useravatar/", "user_" + loginName);
        //保存到数据库
        this.iBaseService.saveUserAvatar(loginName, newName, file);
        return new ResultDTO("头像上传成功!");
    }


    @ApiOperation(value = "检查登录用户role信息-是否具备后台管理权限")
    @GetMapping(value = "/findUserRoleForBackToVue")
    public ResultDTO findUserRoleForBackToVue() {
        log.info("Begin findUserRoleForBackToVue");
        String loginName = TDFSecurityUtils.getCurrentLogin();
        UserInfoDTO userInfoDTO = iBaseService.findByLoginName(loginName);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userInfoDTO.getId()));
        List<RoleDTO> roleDTOS = iRoleService.findByIds(roleIds);

        for (RoleDTO role : roleDTOS) {
            if ("ROLE_ADMIN".equals(role.getRoleName())) {
                return new ResultDTO("1");
            }
        }
        return new ResultDTO("0");
    }
}