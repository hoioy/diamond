package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "userDto对象", description = "用户对象userDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;


    @ApiModelProperty(value = "用户排序")
    private Integer userIndex;

    //@ExcelResources(title = "邮件", order = 4)
    @Email
    @ApiModelProperty(value = "邮件")
    private String email;
    @ApiModelProperty(value = "昵称")
    private String nickname;
    @ApiModelProperty(value = "性别")
    private String gender;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "博客")
    private String blog;
    @ApiModelProperty(value = "标签")
    private String tag;
    @ApiModelProperty(value = "头像相对路径或者id")
    private String avatar;
    @ApiModelProperty(value = "身份证")
    private String idNumber;
    @ApiModelProperty(value = "出生日期")
    private String birthday;
    @ApiModelProperty(value = "用户积分")
    private Integer integral;

    private byte[] avatarContent;

    @ApiModelProperty(value = "经验值")
    private Integer experience;

    @ApiModelProperty(value = "余额")
    private BigDecimal balance;

    @ApiModelProperty(value = "用户元数据")
    private String userMetadata;

    //以下是复合属性
    private String roleId;
    private String deptId;

    public UserInfoDTO(String id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, Integer flag, String remark,
                       String loginName, String password, String userName, String phoneNum, String state,
                       Integer userIndex, String email, String nickname, String gender, String address, String blog, String tag,
                       String avatar, String idNumber, String birthday, Integer integral) {
        this(id, createdBy, createdDate, modifiedBy, modifiedDate, flag, remark,
                loginName, userName, phoneNum, state,
                userIndex, email, nickname, gender, address, blog, tag,
                avatar, idNumber, birthday, integral);
        this.setPassword(password);
    }

    public UserInfoDTO(String id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, Integer flag, String remark,
                       String loginName, String userName, String phoneNum, String state,
                       Integer userIndex, String email, String nickname, String gender, String address, String blog, String tag,
                       String avatar, String idNumber, String birthday, Integer integral) {
        super.setId(id);
        super.setCreatedBy(createdBy);
        super.setCreatedDate(createdDate);
        super.setModifiedBy(modifiedBy);
        super.setModifiedDate(modifiedDate);
        super.setFlag(flag);
        super.setRemark(remark);
        super.setLoginName(loginName);
        super.setUserName(userName);
        super.setPhoneNum(phoneNum);
        super.setState(state);
        this.userIndex = userIndex;
        this.email = email;
        this.nickname = nickname;
        this.gender = gender;
        this.address = address;
        this.blog = blog;
        this.tag = tag;
        this.avatar = avatar;
        this.idNumber = idNumber;
        this.birthday = birthday;
        this.integral = integral;
    }

}

