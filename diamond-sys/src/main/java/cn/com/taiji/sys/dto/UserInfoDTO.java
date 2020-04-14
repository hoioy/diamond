package com.hoioy.diamond.sys.dto;

import com.hoioy.diamond.common.dto.TDFUserDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * 类名称：UserInfoDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@ApiModel(value = "userDto对象", description = "用户对象userDto")
@Data
@NoArgsConstructor
public class UserInfoDTO extends TDFUserDTO {
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
    // 头像路径
    private String avatar;
    // 身份证号
    private String idNumber;
    // 出生日期
    private String birthday;
    // 用户积分
    private Integer integral;

    private String file;

    private byte[] avatarContent;
}
