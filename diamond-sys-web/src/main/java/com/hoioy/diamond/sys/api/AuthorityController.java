package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonSecurityUtils;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@ApiIgnore
@RestController
public class AuthorityController {

    private static Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private IUserInfoService userService;
    @Autowired
    private IMenuService iMenuService;
    @Autowired
    private IRoleUserService iRoleUserService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IRoleService roleService;

    /**
     * 获取用户指定菜单的权限
     *
     * @param menuId
     * @return
     */
    @GetMapping(path = "/permission/hasPermission")
    public ResultDTO<Set<String>> hasPermission(String menuId) {
        String userNames = CommonSecurityUtils.getCurrentLogin();
        String userId = userService.findIdByLoginName(userNames);
        if (StringUtils.isBlank(userId)) {
            throw new SysException("没有找登录用户");
        }

        //TODO 加校验后，这些代码都可以删除
        if (StringUtils.isBlank(menuId) || "index".equalsIgnoreCase(menuId)) {
            logger.info("hasPermission_缺少必要参数menuId,可能是首页刷新情况导致id:{}", userId);
            throw new SysException("hasPermission_缺少必要参数menuId,可能是首页刷新情况导致id:{}", userId);
        }

        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        List<MenuDTO> menuDTOList = iMenuService.findByIds(menuIds);

        Set<String> menuIdSet = new HashSet();
        menuDTOList.forEach(menuDTO -> {
            if (null != menuDTO.getParentId() && menuId.equals(menuDTO.getParentId())) {
                menuIdSet.add(menuDTO.getAuthorityId());
            }
        });

        return new ResultDTO(menuIdSet);
    }

    //使用统一认证服务时，将统一认证中的用户信息，与本系统的权限体系进行绑定
    //目前采用最简单的绑定策略：如果本系统用户表没有统一认证服务中的用户，则本系统默认添加
    @PostMapping(value = "/bindOAuth2User")
    @ResponseBody
    public ResultDTO bindUser(@RequestBody Map userInfo) throws RuntimeException {
        logger.info("bindUser");
        UserInfoDTO dto = new UserInfoDTO();
        String name = (String) userInfo.get("name");
        String loginName = (String) userInfo.get("loginName");
        String email = (String) userInfo.get("email");
        String avatar = (String) userInfo.get("avatar");
        //有一些用户不在用户表中-比如测试用户admin，可能这部分逻辑还要改。
        if (null == loginName) {
            loginName = name;
        }
        dto.setLoginName(loginName);
        String userId = userService.findIdByLoginName(dto.getLoginName());
        dto.setId(userId);
        //如果本系统没有此用户，则新增
        //用户信息填充
        dto.setUserName(name);
        if (StringUtils.isNotEmpty(email)) {
            dto.setEmail(email);
        } else {
            dto.setEmail("未知");
        }
        dto.setAvatar(avatar);
        dto.setState("1");
        //用户所属角色填充
        //TODO 默认角色写死了
        RoleDTO role = roleService.findByRoleName("ROLE_USER");
        //入库
        String id = userService.saveUserWithRoles(dto, Arrays.asList(role.getId()));
        logger.info("bindUser id={}", id);
        logger.info("bindUser dto={}", dto);
        return new ResultDTO(dto);
    }

    // zhoujial
    @GetMapping(value = "/user-details")
    @ResponseBody
    public ResultDTO getUserByToken() {
        UserInfoDTO user = null;
        try {
            final String userName = CommonSecurityUtils.getCurrentLogin();
            String id = userService.findIdByLoginName(userName);
            user = (UserInfoDTO) userService.findById(id);
            user.setPassword(null);
            List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(user.getId()));
            user.setRoleId(String.join(",", roleIds));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultDTO(user);
    }

    // zhoujial 2018/11/19
    @GetMapping(value = "/routers")
    @ResponseBody
    public ResultDTO getRouter(@RequestParam(value = "token", required = false) String token) {
        final String userName = CommonSecurityUtils.getCurrentLogin();
        String userId = userService.findIdByLoginName(userName);

        UserInfoDTO userInfoDTO = (UserInfoDTO) userService.findById(userId);
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userInfoDTO.getId()));
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        Set<String> menuSet = new HashSet(menuIds);

        List<String> roleNameList = new ArrayList();
        List<RoleDTO> roleDTOs = roleService.findByIds(roleIds);
        roleDTOs.forEach(roleDTO -> {
            roleNameList.add(roleDTO.getRoleName());
        });

        List allList = new ArrayList();
        for (String menu : menuSet) {
            List router = new ArrayList();
            router.add(menu);
            router.add(roleNameList);
            allList.add(router);
        }

        return new ResultDTO(allList);
    }
}
