package com.hoioy.diamond.security.jwt.web;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.RedisCommonUtil;
import com.hoioy.diamond.common.util.TDFBeanUtil;
import com.hoioy.diamond.common.util.WebSiteUtil;
import com.hoioy.diamond.security.exception.TDFSecurityException;
import com.hoioy.diamond.security.jwt.JwtAuthenticationRequest;
import com.hoioy.diamond.security.jwt.converter.CustomJwtAccessTokenConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Api(tags = {"01.普通登录接口"})
@RestController
public class AuthenticationController {
    private static final String captchaEnableOn = "on";
    private static final String captchaRedisKeyPre = "diamond:captcha";
    private static final String captchaSameIpLimitRedisKeyPre = "diamond:ipLimitPerMinutes";
    private static final String lockedRedisKeyPre = "diamond:locked";

    //是否启用验证码逻辑
    @Value("${diamond.security.captcha-enable}")
    private String captchaEnable = captchaEnableOn;
    //验证码有效期，单位：秒
    @Value("${diamond.security.captcha-max-wait-second}")
    private long captchaMaxWaitSecond = 600l;
    //同一个IP地址，每分钟限制请求多少次验证码
    @Value("${diamond.security.captcha-same-ip-limit-per-minutes}")
    private long captchaSameIpLimitPerMinutes = 60;

    //允许登录错误次数，对登录错误一定次数的用户进行封锁账号以及 IP 等措施
    @Value("${diamond.security.login.retry-time}")
    private Integer retryTime = 5;
    //被锁定，不允许登录后恢复时间间隔
    @Value("${diamond.security.login.locked-recover-second}")
    private long lockedRecoverSecond = 12 * 3600;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomJwtAccessTokenConverter customJwtAccessTokenConverter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisCommonUtil redisCommonUtil;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/auth")
    public ResultDTO auth(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws RuntimeException, JsonProcessingException {
        String loginName = authenticationRequest.getUsername();
        String lockedRedisKey = lockedRedisKeyPre + ":" + loginName;
        String time = redisCommonUtil.get(lockedRedisKey);
        if (StringUtils.isEmpty(time)) {
            //没有记录，说明之前没有错误认证
            time = retryTime + "";
            redisCommonUtil.set(lockedRedisKey, time, lockedRecoverSecond);
        }

        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new TDFSecurityException("您已经超过今天的重试次数，请明天再试。");
        }
        redisCommonUtil.increment(lockedRedisKey, -1);

        if (captchaEnableOn.equals(captchaEnable)) {
            //验证码校验逻辑启用
            String captchaKey = authenticationRequest.getCaptchaKey();
            if (StringUtils.isEmpty(captchaKey)) {
                throw new TDFSecurityException("验证码Key不可以为空");
            }
            String storedCaptchaCode = redisCommonUtil.get(captchaRedisKeyPre + ":" + captchaKey);
            if (StringUtils.isBlank(storedCaptchaCode) || !storedCaptchaCode.equals(authenticationRequest.getCaptchaValue())) {
                throw new TDFSecurityException("验证码错误或已过期，请刷新验证码");
            }
        }
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = customJwtAccessTokenConverter.generateToken(userDetails);
        redisCommonUtil.remove(lockedRedisKey);
        return new ResultDTO(token);
    }

    /**
     * 前后端分离，无session。后端生成的验证码不能通过session直接拿到，
     * 所以后端生成的验证码会保存在redis中，前端登录时带上key和value，后端根据Key去redis取值，
     * 再和前端传进来的value对比，完成验证码验证
     */
    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/captcha")
    public ResultDTO captcha(HttpServletRequest httpServletRequest) {
        String ip = WebSiteUtil.getIpAddress(httpServletRequest);
        String ipLimitRedisKey = captchaSameIpLimitRedisKeyPre + ":" + ip;
        String time = redisCommonUtil.get(ipLimitRedisKey);
        if (StringUtils.isEmpty(time)) {
            //没有记录，说明之前没有错误认证
            time = captchaSameIpLimitPerMinutes + "";
            redisCommonUtil.set(ipLimitRedisKey, time, 60);
        }
        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new TDFSecurityException("每分钟只能获取60次验证码");
        }

        String captchaCode = (int) ((Math.random() * 9 + 1) * 1000) + "";
        String key = TDFBeanUtil.generateBeanId();
        redisCommonUtil.set(captchaRedisKeyPre + ":" + key, captchaCode, captchaMaxWaitSecond);

        Map<String, String> result = new HashMap<String, String>();
        result.put("captchaKey", key);
        result.put("captchaCode", captchaCode);
        redisCommonUtil.increment(ipLimitRedisKey, -1);
        return new ResultDTO(result);
    }

    //    @GetMapping(value = "/refresh")
//    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) throws IOException {
//        String authToken = request.getHeader(JwtTokenUtil.tokenHeader);
//        final String token = authToken.substring(7);
//        String username = jwtTokenUtil.getUsernameFromToken(token);
//        User user = (User) userDetailsService.loadUserByUsername(username);
////        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
//        if (jwtTokenUtil.isInRefreshDate(token)) {
//            String refreshedToken = jwtTokenUtil.refreshToken(token);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
//        } else {
//            return ResponseEntity.badRequest().body(null);
//        }
//    }

    /**
     * Authenticates the user.
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth = authenticationManager.authenticate(authRequest);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
