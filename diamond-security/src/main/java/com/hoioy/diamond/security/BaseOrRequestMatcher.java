package com.hoioy.diamond.security;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 OrRequestMatcher
 */
public final class BaseOrRequestMatcher implements RequestMatcher {

    private final Log logger = LogFactory.getLog(getClass());
    private final List<RequestMatcher> requestMatchers = new ArrayList<>();

    public boolean matches(HttpServletRequest request) {
        for (RequestMatcher matcher : requestMatchers) {
            if (logger.isDebugEnabled()) {
                logger.debug("Trying to match using " + matcher);
            }
            if (matcher.matches(request)) {
                logger.debug("matched");
                return true;
            }
        }
        logger.debug("No matches found");
        return false;
    }

    @Override
    public String toString() {
        return "BaseOrRequestMatcher [requestMatchers=" + requestMatchers + "]";
    }

    public void addRequestMatcher(RequestMatcher requestMatcher) {
        if (requestMatcher != null) {
            this.requestMatchers.add(requestMatcher);
        }
    }

    public void addAllRequestMatcher(List<RequestMatcher> requestMatcherList) {
        if (CollectionUtil.isNotEmpty(requestMatcherList)) {
            this.requestMatchers.addAll(requestMatcherList);
        }
    }
}
