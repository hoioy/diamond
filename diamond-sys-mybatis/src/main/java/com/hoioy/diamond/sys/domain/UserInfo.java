package com.hoioy.diamond.sys.domain;

import com.hoioy.diamond.common.base.BaseDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    /**
     * 积分
     */
    private Integer integral;

    private String loginName;

    private String nickname;

    private String password;

    private String phoneNum;

    private String state;

    private String tag;

    private Integer userIndex;

    private String userName;
    /**
     * 经验值
     */
    private Integer experience;

    /**
     * 余额
     */
    private BigDecimal balance;


    /**
     * 余额
     */
    private String openid;

}
