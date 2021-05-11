package com.hoioy.diamond.security.qrcode;

import com.hoioy.diamond.common.dto.ResultDTO;
import com.hoioy.diamond.common.util.CommonCacheUtil;
import com.hoioy.diamond.security.BaseSecurityException;
import com.hoioy.diamond.security.ResponseHandler;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 移动端扫描二维码登录
 */
public class BaseQrCodeFilter extends OncePerRequestFilter {
    private final static Logger logger = LoggerFactory.getLogger(BaseQrCodeFilter.class);

    //TODO 完善同一个IP接口调用限制策略，类似Catcha的限制，如5分钟内同一个Ip只能调用5次

    //生成登录的二维码,前端可以根据qrcodeId使用js动态生成二维码图片
    private RequestMatcher idRequestMatcher = new AntPathRequestMatcher("/qrcodeId");
    //移动端扫描二维码后调用此接口，告知服务端允许PC登录登录
    private RequestMatcher authRequestMatcher = new AntPathRequestMatcher("/qrcodeAuth");
    //PC端轮询调用，检测手机端是否已经允许登录
    private RequestMatcher tryRequestMatcher = new AntPathRequestMatcher("/qrcodeTryAuth");

    private CommonCacheUtil commonCacheUtil;

    private ResponseHandler responseHandle;

    private Long expiration;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        if (idRequestMatcher.matches(request)) {
            String qrcodeId = IdUtil.randomUUID();
            commonCacheUtil.set(getQRCodeRedisKey(qrcodeId), "false", expiration);
            responseHandle.success(request, response, new ResultDTO<>(qrcodeId));
            return;
        } else if (authRequestMatcher.matches(request)) {
            String qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId不能空"));
            }

            commonCacheUtil.set(getQRCodeRedisKey(qrcodeId), "true", expiration);
            responseHandle.success(request, response, new ResultDTO<>("扫描成功，允许登录"));
            return;
        } else if (tryRequestMatcher.matches(request)) {
            String qrcodeId = request.getParameter("qrcodeId");
            if (StrUtil.isBlank(qrcodeId)) {
                responseHandle.fail(request, response, new BaseSecurityException(401, "qrcodeId不能空"));
            }

            String cache = commonCacheUtil.get(getQRCodeRedisKey(qrcodeId));
            if (StrUtil.isBlank(cache)) {
                responseHandle.success(request, response, new ResultDTO<>("0:二维码已经过期"));
                return;
            } else if ("false".equals(cache)) {
                responseHandle.success(request, response, new ResultDTO<>("1:手机端未允许登录，请等待手机端确认或者刷新二维码"));
                return;
            }

            //TODO 暂时将手机端的同一用户的token颁发给web端，后续改为生成新的token
            String token = request.getParameter("token");
            if (StrUtil.isBlank(token)) {
                //如果parameter中没有，再从header中取
                token = request.getHeader(HttpHeaders.AUTHORIZATION);
            }

            responseHandle.success(request, response, new ResultDTO(token));
            return;
        }
        chain.doFilter(request, response);
    }

    public CommonCacheUtil getCommonCacheUtil() {
        return commonCacheUtil;
    }

    public void setCommonCacheUtil(CommonCacheUtil commonCacheUtil) {
        this.commonCacheUtil = commonCacheUtil;
    }

    public ResponseHandler getResponseHandle() {
        return responseHandle;
    }

    public void setResponseHandle(ResponseHandler responseHandle) {
        this.responseHandle = responseHandle;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    /**
     * 登录二维码key
     */
    private String getQRCodeRedisKey(String qrcodeId) {
        return "qrcode::" + qrcodeId;
    }
}
