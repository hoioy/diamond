package com.hoioy.jiayin.conf;

import com.hoioy.diamond.common.dto.CommonUserDTO;
import com.hoioy.diamond.security.jwt.annotation.JwtWebSecurityConfigurerAdapter;
import com.hoioy.diamond.sys.service.IMenuService;
import com.hoioy.diamond.sys.service.IRoleMenuService;
import com.hoioy.diamond.sys.service.IRoleUserService;
import com.hoioy.diamond.sys.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends JwtWebSecurityConfigurerAdapter {
    @Autowired
    private IUserInfoService iUserService;
    @Autowired
    private IRoleUserService iRoleUserService;
    @Autowired
    private IRoleMenuService iRoleMenuService;
    @Autowired
    private IMenuService iMenuService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        // 过滤器的安全拦截器的每一次的要求
        http.authorizeRequests().filterSecurityInterceptorOncePerRequest(true)
                .antMatchers("/auth/**",  "/captcha", "/error").permitAll()
                .antMatchers("/sys/user/save").permitAll()//注册用户
                //通过Oauth2登录时绑定用户接口，暂时开启，否则与JwtAuthorizationTokenFilter逻辑冲突（本系统还没有用户所以查不出来）
                .antMatchers("/auth","/bindOAuth2User").permitAll()
                .antMatchers("/wx/user/**").permitAll()
                .antMatchers("/files/**","/user-upload-avatar-rest").permitAll()
                .antMatchers(HttpMethod.GET,"/jiayin/message/**").permitAll()//未登录用户允许查看信息
                .antMatchers("/jiayin/message/page").permitAll()
                .antMatchers("/jiayin/advert/**").permitAll()
                .antMatchers("/jiayin/msgType/**").permitAll()
                .antMatchers("/jiayin/notice/search").permitAll()
                .antMatchers("/jiayin/zonecode/**").permitAll()
                // 使其支持跨域
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .anyRequest().fullyAuthenticated()
                .and().exceptionHandling().accessDeniedHandler(baseJwtAccessDeniedHandler())
                .authenticationEntryPoint(baseJwtAuthenticationEntryPoint());

        http.addFilterAfter(basefiltersecurityinterceptor(), FilterSecurityInterceptor.class)
                .addFilterBefore(baseJwtAuthorizationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //JWT没有csrf问题，需要禁用
        http.csrf().disable()
                //JWT不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // disable page caching
                .headers()
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
        // swagger start TODO
        web.ignoring().antMatchers("/doc.html");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/webjars/**");
        web.ignoring().antMatchers("/v2/api-docs");
        web.ignoring().antMatchers("/v2/api-docs-ext");
        web.ignoring().antMatchers("/configuration/ui");
        web.ignoring().antMatchers("/configuration/security");
        // swagger end TODO
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(baseUserDetailService()).passwordEncoder(basePasswordEncoder());
    }

    //解决spring security 5.x无法注入AuthenticationManager的问题
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public List<String> getRoleIdsByRequestUrl(String requestUrl) {
        // TODO 每次请求都要调用此方法，因此一定要加缓存，使用的是Spring Cache
        // TODO 现在是绝对匹配，不支持模糊匹配，用户可以在此自定义扩展其他资源与角色的匹配逻辑，如支持/**这种模糊匹配
        // TODO menuUrl与requestUrl没有任何关系,所以TaijiAccessDecisionManager的逻辑一直没有生效
        if (requestUrl.startsWith("/")) {
            requestUrl = requestUrl.substring(1);
        }
        List<String> menuIds = iMenuService.findIdsByMenuUrl(requestUrl) ;
        if(CollectionUtils.isEmpty(menuIds)){
            return new ArrayList<>();
        }else{
            return iRoleMenuService.findRoleIdsByMenuIds(menuIds);
        }
    }

    @Override
    public CommonUserDTO getCommonUserDTOByLoginName(String loginName) {
       String userId = iUserService.findIdByLoginName(loginName);
        return (CommonUserDTO) iUserService.findById(userId).get();
    }

    @Override
    public List<String> getRoleIdsByLoginName(String loginName) {
        String userId = iUserService.findIdByLoginName(loginName);
        List<String> roleIds = iRoleUserService.findFirstIdsBySecondId(userId);
        return roleIds;
    }
}
