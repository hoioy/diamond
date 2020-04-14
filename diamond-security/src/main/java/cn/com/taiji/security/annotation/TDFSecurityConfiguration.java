//package com.hoioy.diamond.security.annotation;
//
//import com.hoioy.diamond.security.*;
//import com.hoioy.diamond.service.sys.IMenuService;
//import com.hoioy.diamond.service.sys.IUserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
///**
// * Created by andy on 2019/02/11.
// */
//public class TDFSecurityConfiguration {
//
//    @Autowired
//    private IMenuService menuService;
//
//    @Autowired
//    private IUserInfoService userService;
//
////    @Autowired
////    private AuthenticationManager authenticationManager;
//
////
////    @Bean
////    public TaijiUsernamePasswordAuthenticationFilter loginFilter() throws Exception {
////        TaijiUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new TaijiUsernamePasswordAuthenticationFilter();
////        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customSuccessHandler());
////        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(customFailureHandler());
////        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
////        customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/j_spring_security_check");
////        return customUsernamePasswordAuthenticationFilter;
////    }
////
////    @Bean
////    public TaijiFailureHandler customFailureHandler() {
////        TaijiFailureHandler customFailureHandler = new TaijiFailureHandler();
////        customFailureHandler.setDefaultFailureUrl("/login?error");
////        return customFailureHandler;
////    }
////
////    @Bean
////    public TaijiSuccessHandler customSuccessHandler() {
////        TaijiSuccessHandler customSuccessHandler = new TaijiSuccessHandler();
////        customSuccessHandler.setDefaultTargetUrl("/");
////        return customSuccessHandler;
////    }
//
//    @Bean
//    public MyAccessDeniedHandler accessDeniedHandler() {
//        MyAccessDeniedHandler access = new MyAccessDeniedHandler();
//        access.setErrorPage("403");
//        return access;
//    }
//
//    @Bean
//    public TaijiAccessDecisionManager taijiAccessDecisionManager() {
//        return new TaijiAccessDecisionManager();
//    }
//
//    @Bean
//    public TaijiSecurityMetadataSource taijiSecurityMetadataSource() {
//        TaijiSecurityMetadataSource result = new TaijiSecurityMetadataSource();
//        result.setMenuService(menuService);
//        result.setUserService(userService);
//        return result;
//    }
//
//    @Bean
//    public TaijiUserDetailServiceImpl taijiUserDetailServiceImpl() {
//        TaijiUserDetailServiceImpl result = new TaijiUserDetailServiceImpl();
//        result.setUserService(userService);
//        return result;
//    }
//
//    @Bean
//    public TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor() throws Exception {
//        TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor = new TaijiFilterSecurityInterceptor();
//        taijifiltersecurityinterceptor.setFisMetadataSource(taijiSecurityMetadataSource());
//        taijifiltersecurityinterceptor.setAccessDecisionManager(taijiAccessDecisionManager());
//        return taijifiltersecurityinterceptor;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
