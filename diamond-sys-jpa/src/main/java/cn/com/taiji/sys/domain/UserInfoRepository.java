package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends IBaseRepository<UserInfo, String> {

    /**
     * 根据登录名称查询用户
     */
    UserInfo findByLoginName(String loginName);

    /**
     * 修改头像
     */
    @Modifying
    @Query("update UserInfo u set u.avatarContent=:avatarContent,u.avatar=:avatar  where u.loginName=:loginName")
    void updateAvatar(@Param("loginName") String loginName, @Param("avatar") String avatar, @Param("avatarContent") byte[] avatarContent);
}
