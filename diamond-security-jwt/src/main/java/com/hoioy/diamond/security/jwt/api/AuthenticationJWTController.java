package com.hoioy.diamond.security.jwt.api;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonRedisUtil;
import com.hoioy.diamond.security.AuthenticationRequest;
import com.hoioy.diamond.security.captcha.CaptchaService;
import com.hoioy.diamond.security.exception.BaseSecurityException;
import com.hoioy.diamond.security.jwt.converter.BaseJwtAccessTokenConverter;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@Api(tags = {"01.普通登录接口"})
@RestController
public class AuthenticationJWTController{
    private static final String lockedRedisKeyPre = "base:locked";
    //允许登录错误次数，对登录错误一定次数的用户进行封锁账号以及 IP 等措施
    @Value("${base.security.login.retry-time:5}")
    private Integer retryTime;
    //被锁定，不允许登录后恢复时间间隔
    @Value("${base.security.login.locked-recover-second:43200}")
    private long lockedRecoverSecond ;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BaseJwtAccessTokenConverter baseJwtAccessTokenConverter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private CommonRedisUtil commonRedisUtil;

    @ApiOperation(value = "登录")
    @PostMapping(value = "/auth")
    public ResultDTO auth(@RequestBody AuthenticationRequest authenticationRequest)
            throws RuntimeException, JsonProcessingException {
        String loginName = authenticationRequest.getUsername();
        String lockedRedisKey = lockedRedisKeyPre + ":" + loginName;
        String time = commonRedisUtil.get(lockedRedisKey);
        if (StringUtils.isBlank(time)) {
            //没有记录，说明之前没有错误认证
            time = retryTime + "";
            commonRedisUtil.set(lockedRedisKey, time, lockedRecoverSecond);
        }

        Integer t = Integer.parseInt(time);
        if (t <= 0) {
            throw new BaseSecurityException("您已经超过今天的重试次数，请明天再试。");
        }
        commonRedisUtil.increment(lockedRedisKey, -1);

        captchaService.checkCaptcha(authenticationRequest);
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = baseJwtAccessTokenConverter.generateToken(userDetails);
        commonRedisUtil.remove(lockedRedisKey);
        return new ResultDTO(token);
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
