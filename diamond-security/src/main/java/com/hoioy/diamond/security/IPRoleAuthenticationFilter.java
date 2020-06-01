package com.hoioy.diamond.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * IP角色認證，目前問題是角色不正確的化直接跳到了其他過濾器鏈。角色不匹配没有起到作用
 * @author chixue
 *
 */
public class IPRoleAuthenticationFilter extends OncePerRequestFilter {
	private String targetRole;  
	private List<String> allowedIPAddresses;  
	@Override  
	public void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {  
		// before we allow the request to proceed, we'll first get the user's role  
		// and see if it's an administrator  
		final Authentication authentication = SecurityContextHolder.  
				getContext().getAuthentication();  
		if (authentication != null && targetRole != null) {  
			boolean shouldCheck = false;  
			// look if the user is the target role  
			for (GrantedAuthority authority : authentication.getAuthorities()) {  
				if(authority.getAuthority().equals(targetRole)) {  
					shouldCheck = true;  
					break;  
				}  
				if(!shouldCheck) {  
					// fail the request  
					throw new AccessDeniedException("角色不匹配，您当前的角色为:" +authority.getAuthority());
				}  
			}  
			// if we should check IP, then check  
			if(shouldCheck && allowedIPAddresses.size() > 0) {  
				boolean shouldAllow = false;  
				for (String ipAddress : allowedIPAddresses) {  
					if(req.getRemoteAddr().equals(ipAddress)) {  
						shouldAllow = true;  
						break;  
					}  
				}  

				if(!shouldAllow) {  
					// fail the request  
					throw new AccessDeniedException("Access has been    denied for your IP address:" +req.getRemoteAddr());  
				}  
			}  
		} else {  
			//logger.warn("");  
		}  
		chain.doFilter(req, res);    
	}  
	// accessors (getters and setters) omitted  
	public String getTargetRole() {
		return targetRole;
	}
	public void setTargetRole(String targetRole) {
		this.targetRole = targetRole;
	}
	public List<String> getAllowedIPAddresses() {
		return allowedIPAddresses;
	}
	public void setAllowedIPAddresses(List<String> allowedIPAddresses) {
		this.allowedIPAddresses = allowedIPAddresses;
	}
}  

