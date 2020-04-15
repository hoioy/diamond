package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.DiamondSecurityUtils;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@ApiIgnore
@RestController
public class SysAuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(SysAuthenticationController.class);

    @Autowired
    private IUserInfoService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IRoleUserService iRoleUserService;

    /**
     * 使用太极统一认证服务时，将统一认证中的用户信息，与本系统的权限体系进行绑定
     * 目前采用最简单的绑定策略：如果本系统用户表没有统一认证服务中的用户，则本系统默认添加
     *
     * @param userInfo
     * @return
     * @throws RuntimeException
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/bindDiamondUaaUser")
    @ResponseBody
    public ResultDTO bindUser(@RequestBody Map userInfo) throws RuntimeException {
        logger.info("bindUser");
        UserInfoDTO dto = new UserInfoDTO();
        String name = (String) userInfo.get("name");
        String loginName = (String) userInfo.get("loginName");
        //有一些用户不在太极的用户表中-比如测试用户admin，可能这部分逻辑还要改。
        if (null == loginName) {
            loginName = name;
        }
        dto.setLoginName(loginName);
        UserInfoDTO user = userService.findByLoginName(dto.getLoginName());

        if (user == null) {
            //如果本系统没有此用户，则新增
            //用户信息填充
            dto.setUserName(name);
            dto.setEmail("未知");
            dto.setState("1");
            dto.setAvatar("/common/images/default-head.jpg");
            //用户所属角色填充
            //TODO 默认角色写死了
            RoleDTO role = roleService.findByRoleName("ROLE_USER");
            //入库
            String id = userService.saveUserWithRoles(dto, Arrays.asList(role.getId()));
            logger.info("bindUser id={}", id);
        }

        logger.info("bindUser dto={}", dto);
        return new ResultDTO(dto);
    }

    /**
     * @Description:
     * @Param: [token]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/16
     */
    @GetMapping(value = "/user-details")
    @ResponseBody
    //TODO andyzhao token参数不需要传递，因为token在http的header中传递了，用户对象在security的过滤器链中已经构建，可以
    // 通过调用SecurityUtils.getCurrentLogin()获取
    public ResultDTO getUserByToken(@RequestParam(value = "token", required = false) String token) {
        UserInfoDTO user = null;
        try {
            final String userName = DiamondSecurityUtils.getCurrentLogin();
            user = userService.findByLoginName(userName);
            user.setPassword(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResultDTO(user);
    }

//    public List<Menu> findMenuParents(List<Menu> menuList) {
//        List<Menu> MenuParents = new ArrayList();
//        for (Menu menu : menuList) {
//            if (null != menu.getParentId()) {
//                MenuParents.add(menuRepository.findMenuById(menu.getParentId()));
//            }
//        }
//        if(MenuParents.size()>0){
//            menuList.addAll(findMenuParents(MenuParents));
//        }
//        menuList.addAll(MenuParents);
//        return menuList;
//    }

    /**
     * @Description:
     * @Param: [token]
     * @return: ResultDTO
     * @Author: zhoujial
     * @Date: 2018/11/19
     */
    @GetMapping(value = "/routers")
    @ResponseBody
    public ResultDTO getRouter(@RequestParam(value = "token", required = false) String token) {
        final String userName = DiamondSecurityUtils.getCurrentLogin();
        UserInfoDTO user = userService.findByLoginName(userName);

        Optional<UserInfoDTO> userDTO = userService.findById(user.getId());
        List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userDTO.get().getId()));
        List<String> menuIds = iRoleMenuService.findMenuIdsByRoleIds(roleIds);
        Set<String> menuSet = new HashSet(menuIds);

        List<String> roleNameList = new ArrayList();
        List<RoleDTO> roleDTOs = roleService.findByIds(roleIds);
        roleDTOs.forEach(roleDTO -> {
            roleNameList.add(roleDTO.getRoleName());
        });

//            List<Menu> menuList = new ArrayList();
//            List<String> menuIdList = new ArrayList();
//            menuIdList.addAll(menuSet);
//            for(String id :menuIdList){
//                Menu menu = menuRepository.findById(id).get();
//                menuList.add(menu);
//            }
//            Set<String> menuSetFinal = new HashSet();
//            for (int k = 0; k < findMenuParents(menuList).size(); k++) {
//                menuSetFinal.add(findMenuParents(menuList).get(k).getId());
//            }

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
