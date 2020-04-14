package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Blob;

/**
 * <p>
 * 
 * </p>
 *
 * @author 陈哲
 * @since 2020-03-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo extends BaseDomain {

    private static final long serialVersionUID=1L;

    private String address;

    private String avatar;

    private byte[] avatarContent;

    private String birthday;

    private String blog;

    private String email;

    private String gender;

    private String idNumber;

    private Integer integral;

    private String loginName;

    private String nickname;

    private String password;

    private String phoneNum;

    private String state;

    private String tag;

    private Integer userIndex;

    private String userName;

}
