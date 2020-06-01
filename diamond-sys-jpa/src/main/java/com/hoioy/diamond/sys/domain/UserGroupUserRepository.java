package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseJoinRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户。用户组中间表Repository
 */
@Repository
public interface UserGroupUserRepository extends IBaseJoinRepository<UserGroupUser> {

}
