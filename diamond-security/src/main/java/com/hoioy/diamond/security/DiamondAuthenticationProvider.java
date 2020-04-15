package com.hoioy.diamond.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class DiamondAuthenticationProvider implements AuthenticationProvider {
	@Autowired
    DiamondUserDetailServiceImpl userDetailsService;

    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {


        UserDetails userDetails = (UserDetails)userDetailsService.loadUserByUsername(authentication.getName());
        //username

        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                userDetails, authentication.getCredentials(),userDetails.getAuthorities());
        return result;
    }


    public DiamondUserDetailServiceImpl getUserDetailsService() {
		return userDetailsService;
	}


	public void setUserDetailsService(DiamondUserDetailServiceImpl userDetailsService) {
		this.userDetailsService = userDetailsService;
	}


	public boolean supports(Class authentication) {
         return true;
    }

  
}

 