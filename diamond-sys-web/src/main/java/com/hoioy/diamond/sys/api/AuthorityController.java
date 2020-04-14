package com.hoioy.diamond.sys.api;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.TDFSecurityUtils;
import com.hoioy.diamond.sys.dto.MenuDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.exception.SysException;
import com.hoioy.diamond.sys.service.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApiIgnore
@RestController
@RequestMapping(path = "/permission", method = RequestMethod.POST)
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

    /**
     * 获取用户指定菜单的权限
     */
    @GetMapping(path = "/hasPermission")
    public ResultDTO<Set<String>> hasPermission(@RequestParam(value="menuId", required = false) String menuId) {
        String userNames = TDFSecurityUtils.getCurrentLogin();
        UserInfoDTO findByLoginNameRest = userService.findByLoginName(userNames);
        if (findByLoginNameRest == null) {
            throw new SysException("没有找登录用户");
        }
        String userId = findByLoginNameRest.getId();

        //TODO 加校验后，这些代码都可以删除
        if (StringUtils.isEmpty(menuId) || "index".equalsIgnoreCase(menuId)) {
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
}
