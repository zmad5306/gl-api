package com.example.gl.api.web.controller;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
	
	@RequestMapping(
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE,
			value="/session/user"
		)
	public UserDetails getUser() {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (null != ctx) {
			Authentication auth = ctx.getAuthentication();
			if (null != auth) {
				return (UserDetails) auth.getPrincipal();
			}
		}
		
		return null;
	}

}
