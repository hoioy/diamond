package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.IBaseRepository;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends IBaseRepository<UserInfo> {

    /**
     * 根据登录名称查询用户
     */
    @Query("select u.id from UserInfo u where u.loginName=:loginName")
    String findIdByLoginName(@Param("loginName") String loginName);

    String userCompositePageJPQL = " from UserInfo u " +
            " left join RoleUser ru on ru.userId=u.id " +
            " left join DeptUser du on du.userId=u.id " +
            " where (:#{#userInfoDTO.roleId} is null or ru.roleId = :#{#userInfoDTO.roleId}) " +
            " and (:#{#userInfoDTO.deptId} is null or du.deptId = :#{#userInfoDTO.deptId}) " +
            " and (:#{#userInfoDTO.email} is null or u.email  LIKE CONCAT('%',:#{#userInfoDTO.email},'%')) " +
            " and (:#{#userInfoDTO.loginName} is null or u.loginName  LIKE CONCAT('%',:#{#userInfoDTO.loginName},'%')) " +
            " and (:#{#userInfoDTO.userName} is null or u.userName  LIKE CONCAT('%',:#{#userInfoDTO.userName},'%')) " +
            " and (:#{#userInfoDTO.phoneNum} is null or u.phoneNum  LIKE CONCAT('%',:#{#userInfoDTO.phoneNum},'%')) " +
            " and (:#{#userInfoDTO.state} is null or u.state  LIKE CONCAT('%',:#{#userInfoDTO.state},'%')) " +
            " and (:#{#userInfoDTO.gender} is null or u.gender  LIKE CONCAT('%',:#{#userInfoDTO.gender},'%')) " +
            " and (:#{#userInfoDTO.nickname} is null or u.nickname  LIKE CONCAT('%',:#{#userInfoDTO.nickname},'%')) ";

    @Query(value = "select distinct new com.hoioy.diamond.sys.dto.UserInfoDTO(u.id, u.createdBy, u.createdDate, u.modifiedBy, u.modifiedDate, " +
            "u.flag, u.remark,u.loginName, u.password, u.userName, u.phoneNum, u.state, u.userIndex," +
            " u.email, u.nickname, u.gender, u.address, u.blog, u.tag,u.avatar, u.idNumber, u.birthday, u.integral) " + userCompositePageJPQL,
            countQuery = " select count(distinct u.id) " + userCompositePageJPQL)
    Page<UserInfoDTO> getUserOnlyByRoleIdOrDeptIdPage(@Param("userInfoDTO") UserInfoDTO userInfoDTO, Pageable pageable);

}
