package com.hoioy.diamond.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DiamondAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint{
	public DiamondAuthenticationEntryPoint(String loginFormUrl) {
		super(loginFormUrl);
		// TODO Auto-generated constructor stub
	}

	private static final Log logger = LogFactory.getLog(DiamondAuthenticationEntryPoint.class);
	 
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    @Autowired
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
 
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
 
        String redirectUrl = null;
 
        String url = request.getRequestURI();
 
        if (logger.isDebugEnabled()) {
            logger.debug("url:" + url);
        }
 
        // 非ajax请求
        if (url.indexOf("ajax") == -1) {
 
            if (this.isUseForward()) {
 
                if (this.isForceHttps() && "http".equals(request.getScheme())) {
                    // First redirect the current request to HTTPS.
                    // When that request is received, the forward to the login page will be used.
                    redirectUrl = buildHttpsRedirectUrlForRequest(httpRequest);
                }
                if (redirectUrl == null) {
                    String loginForm= determineUrlToUseForThisRequest(httpRequest, httpResponse, authException);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Server side forward to: " + loginForm);
                    }
 
                    RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginForm);
 
                    dispatcher.forward(request, response);
 
                    return;
                }
            } else {
                // redirect to login page. Use https if forceHttps true
 
                redirectUrl = buildRedirectUrlToLoginPage(httpRequest, httpResponse, authException);
 
            }
 
            redirectStrategy.sendRedirect(httpRequest, httpResponse, redirectUrl);
        } else {
            // ajax请求，返回json，替代redirect到login page
            if (logger.isDebugEnabled()) {
                logger.debug("ajax request or post");
            }
 
            ObjectMapper objectMapper = new ObjectMapper();
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(),
                    JsonEncoding.UTF8);
            try {
                objectMapper.writeValue(jsonGenerator, "");
            } catch (JsonProcessingException ex) {
                throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }
    }
 
}
