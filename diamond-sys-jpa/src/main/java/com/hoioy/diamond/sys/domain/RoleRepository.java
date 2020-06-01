package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends IBaseRepository<Role> {

    Role findByRoleName(String roleName);
}
