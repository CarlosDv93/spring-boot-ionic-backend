package com.carlosdv93.udemy.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.carlosdv93.udemy.cursomc.security.UserSS;

public class UserService {
	
	public static UserSS authenticaded() {
		
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
