package com.vizoni.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.vizoni.cursomc.services.DBService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	// pega o valor da chave do application-dev.properties
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	// método para instanciar o banco no profile de dev
	@Bean
	public boolean instantiateDatabase() throws ParseException {
		if(!"create".equals(this.strategy)) {
			return false;
		}
		dbService.instantiateTestDatabase();
		return true;
	}
	
}
