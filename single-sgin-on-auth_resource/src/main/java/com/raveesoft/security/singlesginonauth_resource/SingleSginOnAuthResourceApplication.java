package com.raveesoft.security.singlesginonauth_resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.raveesoft.security.filter.JwtFilter;

import io.jsonwebtoken.lang.Collections;

@SpringBootApplication
public class SingleSginOnAuthResourceApplication {

	@Value("${services.auth}")
    private String authService;
	
	public static void main(String[] args) {
		SpringApplication.run(SingleSginOnAuthResourceApplication.class, args);
	}
	
	 @Bean
	    public FilterRegistrationBean jwtFilter() {
	        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	        registrationBean.setFilter(new JwtFilter());
	        registrationBean.setInitParameters(java.util.Collections.singletonMap("services.auth", authService));
	        registrationBean.addUrlPatterns("/protected-resource");

	        return registrationBean;
	    }

}
