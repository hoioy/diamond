package com.hoioy.diamond.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SessionTimeOutInterceptor extends LoginUrlAuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(SessionTimeOutInterceptor.class);

    /**
     * 转发策略引用
     */
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    /**
     * 构造方法
     *
     * @param loginFormUrl
     */
    public SessionTimeOutInterceptor(String loginFormUrl) {
        super(loginFormUrl);
    }

    /**
     * 跳转到登录页面
     */
    public void commence(HttpServletRequest request,
                         HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        String redirectUrl = null;

        if (this.isUseForward()) {

            if (this.isForceHttps() && "http".equals(request.getScheme())) {
                // First redirect the current request to HTTPS.
                // When that request is received, the forward to the login page
                // will be used.
                redirectUrl = buildHttpsRedirectUrlForRequest(request);
            }

            if (redirectUrl == null) {
                String loginForm = determineUrlToUseForThisRequest(request,
                        response, authException);

                if (logger.isDebugEnabled()) {
                    logger.debug("Server side forward to: " + loginForm);
                }

                RequestDispatcher dispatcher = request
                        .getRequestDispatcher(loginForm);

                dispatcher.forward(request, response);

                return;
            }
        } else {

//			if (!AjaxUtil.isAjaxRequest(request))
//				redirectUrl = buildRedirectUrlToLoginPage(request, response,
//						authException);
        }

//		if (!AjaxUtil.isAjaxRequest(request))
//			redirectStrategy.sendRedirect(request, response, redirectUrl);
//		else {
//			// 在响应头设置session状态
//			response.setStatus(HttpStatus.UNAUTHORIZED.value());
//		}
    }
}
