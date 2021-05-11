package com.hoioy.diamond.common.service;

import java.util.Set;

/**
 * spring security工具类，获取当前登录用户等等
 */
public abstract class CommonSecurityService {
    public static CommonSecurityService instance;

    public CommonSecurityService() {
        instance = this;
    }

    /**
     * 获取当前登录用户的LoginName
     */
    public abstract String getCurrentLoginName();

    /**
     * 获取当前登录用户的权限集合
     */
    public abstract Set<String> getCurrentAuthorities();

    /**
     * 加密密码
     *
     * @param rawPassword 未加密原始密码
     * @return 加密后字符串
     */
    public abstract String encodePassword(String rawPassword);

//    /**
//     *是否是登录用户
//     */
//    boolean isAuthenticated();

//    /**
//     * 检查token是否是diamondbase的
//     * @param token
//     * @return
//     */
//    Boolean isBaseJWTToken(String token);

//    /**
//     * token提取
//     * @param request
//     * @return
//     */
//
//    String extractToken(HttpServletRequest request);
}
