package com.hoioy.diamond.sys.mapper;

import com.hoioy.diamond.common.base.IBaseJoinMapper;
import com.hoioy.diamond.sys.domain.UserGroupUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGroupUserMapper extends IBaseJoinMapper<UserGroupUser> {

    List<String> findGroupIdsByUserIds(@Param("userIds") List<String> userIds);

    List<String> findUserIdsByGroupIds(@Param("groupIds") List<String> groupIds);
}
