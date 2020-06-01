package com.hoioy.diamond.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/***
 * 自定义成功处理器
 *
 * @author chixue
 */
public class BaseSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private String LOCAL_SERVER_URL;

    protected final Log logger = LogFactory.getLog(this.getClass());

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        // 存放authentication到SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        // 在session中存放security context,方便同一个session中控制用户的其他操作
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

		/*Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		if (roles.contains("8c93e2e738f4459c9073ac6d53f8de36")) {
			response.sendRedirect(basePath + "index");
			return;
		} else if (roles.contains("0d303477d05a43108e29c602bb990f4a")) {
			response.sendRedirect(basePath + "index_oc");
			return;
		}*/
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if (savedRequest == null) {
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        String targetUrlParameter = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl()
                || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
            requestCache.removeRequest(request, response);
            super.onAuthenticationSuccess(request, response, authentication);
            return;
        }
        clearAuthenticationAttributes(request);
        // Use the DefaultSavedRequest URL
        String targetUrl = savedRequest.getRedirectUrl();
        logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);

        getRedirectStrategy().sendRedirect(request, response, this.getDefaultTargetUrl());
    }

    public void setRequestCache(RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}