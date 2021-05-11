package com.hoioy.diamond.sys.impl;

import com.hoioy.diamond.common.base.BaseJoinServiceImpl;
import com.hoioy.diamond.sys.domain.RoleUser;
import com.hoioy.diamond.sys.dto.RoleUserJoinDTO;
import com.hoioy.diamond.sys.mapper.RoleUserMapper;
import com.hoioy.diamond.sys.service.IRoleUserService;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleUserServiceImpl extends BaseJoinServiceImpl<RoleUserMapper, RoleUser, RoleUserJoinDTO>
        implements IRoleUserService<RoleUser> {

    /**
     * 保存角色用户的分配
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRoleUsers(String[] userIds, String preUserIds, String roleId) {
        String joinUsersIDs = ArrayUtil.join(userIds, ",");
        if (joinUsersIDs == null) {
            joinUsersIDs = "";
        }
        if (preUserIds == null) {
            preUserIds = "";
        }
        if (userIds == null) {
            userIds = new String[0];
        }
        //所有被选中的ID数组转list
        List<String> userIdList = Arrays.asList(userIds);
        //旧的被选中的数组转list
        List<String> preUserIdList = Arrays.asList(preUserIds.split(","));
        if (roleId != null && roleId.length() > 0) {
            // 求差集
            List<String> differenceSet = userIdList.stream().filter(t -> !preUserIdList.contains(t)).collect(Collectors.toList());
            List<String> differenceSet2 = preUserIdList.stream().filter(t -> !userIdList.contains(t)).collect(Collectors.toList());
            differenceSet.removeAll(differenceSet2);//此处指的是将与l2重复的删除
            differenceSet.addAll(differenceSet2);//此处指加上l2
            for (int i = 0; i < differenceSet.size(); i++) {
                String ssd = differenceSet.get(i);
                //取差集中在旧ID中进行删除
                if (preUserIds.contains(ssd) && preUserIds != null && !"".equals(preUserIds) && !"".equals(ssd) && ssd != null) {
                    RoleUserJoinDTO pk = new RoleUserJoinDTO();
                    pk.setRoleId(roleId);
                    pk.setUserId(differenceSet.get(i));
                    remove(pk);
                    //取差集中在新ID中进行存储
                } else if (joinUsersIDs.contains(differenceSet.get(i)) && differenceSet.get(i) != null && !"".equals(differenceSet.get(i)) && joinUsersIDs != null
                ) {
                    RoleUserJoinDTO pk = new RoleUserJoinDTO();
                    pk.setRoleId(roleId);
                    pk.setUserId(differenceSet.get(i));
                    create(pk);
                }
            }
        }
    }


    @Override
    public List<String> findUserIdsByRoleIds(List<String> roleIds) {
        return super.findSecondIdsByFirstIds(roleIds);
    }

    @Override
    public List<String> findRoleIdsByUserIds(List<String> userIds) {
        return super.findFirstIdsBySecondIds(userIds);
    }
}