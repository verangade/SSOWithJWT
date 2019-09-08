package com.raveesoft.security.singlesginonauth_service.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raveesoft.security.singlesginonauth_service.auth.CookieUtil;
import com.raveesoft.security.singlesginonauth_service.auth.JWTUtil;

@Controller
public class LoginController {
	
	
	private static final String jwtTokenCookieName = "JWT-Token";
	private static final String signingKey = "signingKey";
	private static final Map<String,String> credentials = new HashMap<String,String>();
	
	public LoginController() {
        credentials.put("hellokoding", "hellokoding");
        credentials.put("hellosso", "hellosso");
    }
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/login";
	}
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(HttpServletResponse httpServletResponse, String userName , String password, String redirect, Model model) {
		if(userName == null || !credentials.containsKey(userName) || !credentials.get(userName).equals(password)  ) {
			model.addAttribute("error","Invalid username or password");
			return "login";
		}
		
		String token =  JWTUtil.generateToken(signingKey, userName);
		CookieUtil.create(httpServletResponse,jwtTokenCookieName, token, false, -1, "localhost");
		return "redirect: "+redirect;
	}
}
