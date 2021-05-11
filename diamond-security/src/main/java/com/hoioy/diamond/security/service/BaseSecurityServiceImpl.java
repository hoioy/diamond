package com.hoioy.diamond.security.service;

import com.hoioy.diamond.common.service.CommonSecurityService;
import com.hoioy.diamond.security.BaseAuthenticationToken;
import cn.hutool.core.collection.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BaseSecurityServiceImpl extends CommonSecurityService {
    private Logger logger = LoggerFactory.getLogger(BaseSecurityServiceImpl.class);
    private PasswordEncoder passwordEncoder;

    public BaseSecurityServiceImpl(PasswordEncoder passwordEncoder) {
        super();
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String getCurrentLoginName() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String loginName = null;
        //TODO 暂时先这么写,在新增用户和角色接口中获取当前用户空指针,跟AnonymousAuthenticationFilter定义的没有登陆用户保持一致
        if (authentication == null) {
            logger.debug("anonymousUser");
            return "anonymousUser";
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                loginName = userDetails.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                loginName = (String) authentication.getPrincipal();
            } else if (authentication instanceof AbstractAuthenticationToken) {
                loginName = authentication.getName();
            }
        } else if (authentication instanceof BaseAuthenticationToken) {
            logger.debug("authentication is BaseAuthenticationToken");
            loginName = (String) authentication.getPrincipal();
            //该实现类不能判断是否属于JwtAuthenticationToken 因为没有引入相关包
        } else {
            loginName = authentication.getName();
        }
        return loginName;
    }

    @Override
    public Set<String> getCurrentAuthorities() {
        Collection<? extends GrantedAuthority>  grantedAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        Set<String> cs = new HashSet<>();
        if(CollectionUtil.isNotEmpty(grantedAuthorities)) {
            grantedAuthorities.forEach(g -> {
                cs.add(g.getAuthority());
            });
        }

        return cs;
    }

    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

//
//    @Override
//    public boolean isAuthenticated() {
//        SecurityContext securityContext = SecurityContextHolder.getContext();
//
//        final Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
//
//        if (authorities != null) {
//            for (GrantedAuthority authority : authorities) {
//                if (authority.getAuthority().equals(CommonStatic.SECURITY_ANONYMOUS)) {
//                    return false;
//                }
//            }
//        }
//
//        return true;
//    }
//
//    @Override
//    public Boolean isBaseJWTToken(String token) {
//        if (StrUtil.isNotBlank(token) && token.startsWith(Base_Jwt_token_Prefix)) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public String extractToken(HttpServletRequest request) {
//        String authToken = request.getParameter("token");
//        if (StrUtil.isBlank(authToken)) {
//            //如果parameter中没有，再从header中取
//            final String requestHeader = request.getHeader(tokenHeader);
//            if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
//                authToken = requestHeader.substring(7);
//            } else {
//                logger.warn("couldn't find bearer string, will ignore the header");
//            }
//        }
//        return authToken;
//    }
}
