package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseJoinRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户。用户组中间表Repository
 */
@Repository
public interface UserGroupUserRepository extends IBaseJoinRepository<UserGroupUser, String> {
    @Query("select k.groupId from UserGroupUser k where k.userId in (:userIds)")
    List<String> findGroupIdsByUserIds(@Param("userIds") List<String> userIds);

    @Query("select k.userId from UserGroupUser k where k.groupId in (:groupIds)")
    List<String> findUserIdsByGroupIds(@Param("groupIds") List<String> groupIds);
}
