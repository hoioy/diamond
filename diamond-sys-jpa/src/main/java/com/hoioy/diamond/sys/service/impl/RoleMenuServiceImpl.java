package com.hoioy.diamond.sys.service.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.sys.domain.RoleMenu;
import com.hoioy.diamond.sys.domain.RoleMenuRepository;
import com.hoioy.diamond.sys.dto.RoleMenuJoinDTO;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleMenuServiceImpl extends BaseJoinServiceImpl<RoleMenuRepository, RoleMenu, RoleMenuJoinDTO>
        implements IRoleMenuService<RoleMenu> {

    /**
     * 保存角色菜单的分配
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveRoleMenusForRest(String[] menuIds, List preMenuIds, String roleId) {
        String joinUsersIDs = StringUtils.join(menuIds, ",");
        if (joinUsersIDs == null) {
            joinUsersIDs = "";
        }
        if (preMenuIds == null) {
            preMenuIds = new ArrayList();
        }
        if (menuIds == null) {
            menuIds = new String[0];
        }
        //所有被选中的ID数组转list
        List<String> userIdList = Arrays.asList(menuIds);
        //旧的被选中的数组转list
        List<String> preUserIdList = preMenuIds;
        if (roleId != null && roleId.length() > 0) {
            // 求差集
            List<String> differenceSet = userIdList.stream().filter(t -> !preUserIdList.contains(t)).collect(Collectors.toList());
            List<String> differenceSet2 = preUserIdList.stream().filter(t -> !userIdList.contains(t)).collect(Collectors.toList());
            differenceSet.removeAll(differenceSet2);//此处指的是将与l2重复的删除
            differenceSet.addAll(differenceSet2);//此处指加上l2
            for (int i = 0; i < differenceSet.size(); i++) {
                String ssd = differenceSet.get(i);
                //取差集中在旧ID中进行删除
                if (preMenuIds != null && preMenuIds.contains(ssd) && !"".equals(ssd) && ssd != null) {
                    RoleMenuJoinDTO roleMenu = new RoleMenuJoinDTO();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(differenceSet.get(i));
                    remove(roleMenu);
                    //取差集中在新ID中进行存储
                } else if (joinUsersIDs.contains(differenceSet.get(i)) && differenceSet.get(i) != null && !"".equals(differenceSet.get(i)) && joinUsersIDs != null && !"".equals(joinUsersIDs)) {
                    RoleMenuJoinDTO roleMenu = new RoleMenuJoinDTO();
                    roleMenu.setRoleId(roleId);
                    roleMenu.setMenuId(differenceSet.get(i));
                    create(roleMenu);
                }
            }
        }
    }

    @Override
    public List<String> findRoleIdsByMenuIds(List<String> menuIds) {
        return super.findFirstIdsBySecondIds(menuIds);
    }

    @Override
    public List<String> findMenuIdsByRoleIds(List<String> roleIds) {
        return super.findSecondIdsByFirstIds(roleIds);
    }
}