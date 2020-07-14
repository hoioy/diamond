package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "userDto对象", description = "用户对象userDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends CommonUserDTO {
    private static final long serialVersionUID = 1170018455276020707L;

    //	@ExcelResources(title = "用户序号", order = 1)
    private Integer userIndex;
    //	@Email
//@ExcelResources(title = "邮件", order = 4)
    private String email;
    /**
     * 昵称
     */
    private String nickname;
    // 性别
    private String gender;
    // 居住地
    private String address;
    // 博客
    private String blog;
    // 标签
    private String tag;
    // 头像相对路径或者id
    private String avatar;
    // 身份证号
    private String idNumber;
    // 出生日期
    private String birthday;
    // 用户积分
    private Integer integral;

    private byte[] avatarContent;

    //以下是复合属性
    private String roleId;
    private String deptId;

    public UserInfoDTO(String id, String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate, Integer flag, String remark,
                       String loginName, String password, String userName, String phoneNum, String state,
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
        super.setPassword(password);
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
