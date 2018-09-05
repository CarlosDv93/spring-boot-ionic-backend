package com.carlosdv93.udemy.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.carlosdv93.udemy.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbservice;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		dbservice.instantiateTestDatabase();
		return true;
	}

}
