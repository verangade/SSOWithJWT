package com.raveesoft.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.raveesoft.security.auth.JWTUtil;

public class JwtFilter extends OncePerRequestFilter{
	
	private static final String jwtTokenCookieName = "JWT-TOKEN";
	private static final String signingKey = "signingKey";

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
			throws ServletException, IOException {
		String userName = JWTUtil.getSubject(httpServletRequest, jwtTokenCookieName, signingKey);
		if(userName  == null) {
			String authService = this.getFilterConfig().getInitParameter("services.auth");
			httpServletResponse.sendRedirect(authService + "?redirect="+httpServletRequest.getRequestURL());
		}else {
			httpServletRequest.setAttribute("username", userName);
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}
		
		
	}

}
