package com.hoioy.sample.conf;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import com.hoioy.diamond.common.service.CommonSecurityService;
import com.hoioy.diamond.security.BasePasswordEncoder;
import com.hoioy.diamond.security.BaseSecurityUser;
import com.hoioy.diamond.security.BaseUserDetailServiceImpl;
import com.hoioy.diamond.security.FormLoginFailureHandler;
import com.hoioy.diamond.security.authorization.BaseDynamicAuthorization;
import com.hoioy.diamond.security.captcha.BaseCaptchaConfigurer;
import com.hoioy.diamond.security.jwt.BaseJwtAccessDeniedHandler;
import com.hoioy.diamond.security.jwt.BaseJwtAuthenticationEntryPoint;
import com.hoioy.diamond.security.jwt.BaseJwtSuccessHandler;
import com.hoioy.diamond.security.jwt.configurer.BaseJwtTokenAuthenticationConfigurer;
import com.hoioy.diamond.security.limit.BaseRetryLimitConfigurer;
import com.hoioy.diamond.security.qrcode.BaseQrCodeConfigurer;
import com.hoioy.diamond.security.service.BaseSecurityServiceImpl;
import com.hoioy.diamond.security.sms.SmsSecurityConfigurer;
import com.hoioy.diamond.security.sms.SmsThirdSendService;
import com.hoioy.diamond.security.sms.SmsUserDetailsService;
import com.hoioy.diamond.sys.dto.RoleDTO;
import com.hoioy.diamond.sys.dto.UserInfoDTO;
import com.hoioy.diamond.sys.service.*;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 这些配置要封装到diamond security内部
    @Value("${base.security.jwt.secret:mySecret}")
    private String secret;

    @Value("${base.security.jwt.jwkSetUri}")
    private String jwkSetUri;

    @Value("${base.security.jwt.expiration:60}")
    // 冒号：默认值语法
    //单位：秒，默认配置1星期：3600*24*7=604800
    private Long expiration;

    @Value("${base.security.jwt.refresh:1800}")
    //冒号：默认值语法
    //单位：秒，默认配置1星期：60*30
    private Long refresh;

    //是否启用验证码逻辑
    @Value("${base.security.captcha-enable:on}")
    private String captchaEnable;

    //验证码有效期，单位：秒
    @Value("${base.security.captcha-max-wait-second:600}")
    private long captchaMaxWaitSecond;

    //同一个IP地址，每分钟限制请求多少次验证码
    @Value("${base.security.captcha-same-ip-limit-per-minutes:60}")
    private long captchaSameIpLimitPerMinutes;

    @Value("${base.security.login.retry-time:5}")
    private Integer retryTime = 5;

    //被锁定，不允许登录后恢复时间间隔
    @Value("${base.security.login.locked-recover-second:43200}")
    private long lockedRecoverSecond = 43200;

    @Value("${base.security.login.qrcode.expiration:120}")
    private Long qrCodeExpiration;

    //获取当前用户的工具类
    @Bean
    public CommonSecurityService baseSecurityService() {
        return new BaseSecurityServiceImpl(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 过滤器的安全拦截器的每一次的要求
        http.authorizeRequests().filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/error").permitAll()
                .antMatchers("/phoneCodeAuth", "/sendSmsCode").permitAll()
                //通过Oauth2登录时绑定用户接口，暂时开启，否则与JwtAuthorizationTokenFilter逻辑冲突（本系统还没有用户所以查不出来）
                .antMatchers("/bindOAuth2User").permitAll()
                .antMatchers("/file/**").permitAll()
                // 支持跨域
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().access("@baseDynamicAccess.dynamicAccess(request,authentication)");

        http.exceptionHandling().accessDeniedHandler(baseJwtAccessDeniedHandler())
                .authenticationEntryPoint(baseJwtAuthenticationEntryPoint());
        //开启oauth登陆
        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        // 支持Form表单登录
        http.formLogin()
                .successHandler(baseJwtSuccessHandler())
                .failureHandler(new FormLoginFailureHandler());

        // 支持前后端分离刷新和校验token
        http.apply(new BaseJwtTokenAuthenticationConfigurer<>());

        //支持生成校验验证码
        http.apply(new BaseCaptchaConfigurer<>(getApplicationContext()))
                .addCheckPointRequestMatcher(new AntPathRequestMatcher("/ccc")) // 自定义执行验证码校验的url路径
                .captchaEnable(captchaEnable)
                .captchaMaxWaitSecond(captchaMaxWaitSecond)
                .captchaSameIpLimitPerMinutes(captchaSameIpLimitPerMinutes);

        //认证接口限制相关逻辑
        http.apply(new BaseRetryLimitConfigurer<>(getApplicationContext()))
                .retryTime(retryTime)
                .lockedRecoverSecond(lockedRecoverSecond);

        //支持移动端扫描二维码登录
        http.apply(new BaseQrCodeConfigurer<>(getApplicationContext()))
                .expiration(qrCodeExpiration);
        //支持手机短信验证码登录
        http.apply(new SmsSecurityConfigurer<>(getApplicationContext())).enable("off")
                //前后端分离
                .authenticationSuccessHandler(baseJwtSuccessHandler());

        //JWT没有csrf问题，需要禁用
        http.csrf().disable();

        //JWT不使用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // disable page caching
        http.headers()
                // 允许同源的网页嵌套
                .frameOptions().sameOrigin()
                .cacheControl();

        //前后端分离，开启cors
        http.cors();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决静态资源被拦截的问题
        web.ignoring().antMatchers("/images/**", "/css/**", "/js/**", "/favicon.ico", "/index.html", "/login.html");

        // TODO 此处忽略的URL过多，可能不太安全,建议根据需求情况适当开启
        web.ignoring().antMatchers("/**/*.js", "/lang/*.json", "/**/*.css", "/**/*.js", "/**/*.map", "/**/*.html", "/**/*.png"
                , "/**/*.ttf", "/**/*.svg", "/**/*.woff");

        // swagger start
        web.ignoring().antMatchers("/doc.html");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/v2/api-docs");
        web.ignoring().antMatchers("/v2/api-docs-ext");
        web.ignoring().antMatchers("/configuration/ui");
        web.ignoring().antMatchers("/configuration/security");
        // swagger end
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(baseUserDetailService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public BaseJwtSuccessHandler baseJwtSuccessHandler() {
        return new BaseJwtSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BasePasswordEncoder();
    }

    @Bean
    public BaseJwtAuthenticationEntryPoint baseJwtAuthenticationEntryPoint() {
        return new BaseJwtAuthenticationEntryPoint();
    }

    @Bean
    public BaseJwtAccessDeniedHandler baseJwtAccessDeniedHandler() {
        return new BaseJwtAccessDeniedHandler();
    }

    //解决spring security 5.x无法注入AuthenticationManager的问题
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //TODO 以下代码要换一种写法
    @Autowired
    private IUserInfoService iUserService;

    @Autowired
    private IRoleUserService iRoleUserService;

    @Autowired
    private IRoleMenuService iRoleMenuService;

    @Autowired
    private IMenuService iMenuService;

    @Bean
    public BaseDynamicAuthorization baseDynamicAccess() {
        return new BaseDynamicAuthorization() {
            @Override
            protected Set<String> getRequiredAuthoritySet(HttpServletRequest request) {
                // TODO 每次请求都要调用此方法，因此一定要加缓存，TDF使用的是Spring Cache
                // TODO 现在是绝对匹配，不支持模糊匹配，用户可以在此自定义扩展其他资源与角色的匹配逻辑，如支持 /** 这种模糊匹配
                // TODO menuUrl与requestUrl没有任何关系,所以TaijiAccessDecisionManager的逻辑一直没有生效
                String requestUrl = request.getRequestURI();
                if (requestUrl.startsWith("/")) {
                    requestUrl = requestUrl.substring(1);
                }
                List<String> menuIds = iMenuService.findIdsByMenuUrl(requestUrl);
                if (CollectionUtils.isEmpty(menuIds)) {
                    return new HashSet<>();
                } else {
                    return new HashSet<>(iRoleMenuService.findRoleIdsByMenuIds(menuIds));
                }
            }
        };
    }

    @Bean
    public UserDetailsService baseUserDetailService() {
        return new BaseUserDetailServiceImpl() {

            @Override
            public CommonUserDTO findCommonUserDTOByLoginName(String loginName) {
                String userId = iUserService.findIdByLoginName(loginName);
                return iUserService.findWithPasswordById(userId);
            }

            @Override
            public List<String> findRoleIdsByLoginName(String loginName) {
                String userId = iUserService.findIdByLoginName(loginName);
                return iRoleUserService.findFirstIdsBySecondId(userId);
            }
        };
    }

    /**
     * 短信验证码登录
     * 自定义第三方发送短信服务
     *
     * @return SmsThirdSendService
     */
    @Bean
    public SmsThirdSendService smsThirdSendService() {
        return data -> {
            System.out.println("测试");
            return null;
        };
    }

    /**
     * 短信验证码登录
     * 根据手机号获取用户详情的service
     *
     * @return SmsUserDetailsService
     */
    @Bean
    public SmsUserDetailsService smsUserDetailsService() {
        return new SmsUserDetailsService() {
            @Autowired
            private IUserInfoService iUserInfoService;
            @Autowired
            private IRoleUserService iRoleUserService;
            @Autowired
            private IRoleService roleService;

            @Override
            @Transactional
            public UserDetails loadByPhoneNum(String phoneNum) {

                CommonUserDTO user = iUserInfoService.findByPhoneNum(phoneNum);

                Collection<GrantedAuthority> grantedAuths;

                //如果根据手机号查询不到用户直接新增该用户
                if (user == null) {
                    user = new UserInfoDTO();
                    user.setPhoneNum(phoneNum);
                    user.setState("1");
                    //如果没有角色，设置默认的ROLE_USER角色
                    RoleDTO role = roleService.findByRoleName("ROLE_USER");

                    String id = iUserInfoService.saveUserWithRoles((UserInfoDTO) user, Arrays.asList(role.getId()));
                    user.setId(id);
                    //设置默认权限
                    grantedAuths = Arrays.asList(new SimpleGrantedAuthority(role.getId()));
                } else {
                    grantedAuths = obtionGrantedAuthorities(user.getId());
                }


                /**
                 *      封装成spring security的user
                 *      在这里把用户的手机号封装到User中的loginName字段中,如果为空会报错,同时在获取当前用户时可以直接获取当前用户
                 *
                 */

                BaseSecurityUser userdetail = new BaseSecurityUser(user, user.getPhoneNum(), "", grantedAuths);
                return userdetail;
            }

            // 取得用户的权限
            private Set<GrantedAuthority> obtionGrantedAuthorities(String userId) {
                Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
                // 获取用户角色
                List<String> roleIds = iRoleUserService.findRoleIdsByUserIds(Arrays.asList(userId));
                if (CollectionUtil.isNotEmpty(roleIds)) {
                    roleIds.forEach(roleId -> {
                        authSet.add(new SimpleGrantedAuthority(roleId));
                    });
                }
                return authSet;
            }
        };
    }
}
