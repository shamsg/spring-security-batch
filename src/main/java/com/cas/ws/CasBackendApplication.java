package com.cas.ws;

import com.cas.ws.io.repositories.TrxHistoryRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cas.ws.io.repositories.UserRepository;
import com.cas.ws.security.AppProperties;


@SpringBootApplication
@ComponentScan
@EnableMongoRepositories(basePackageClasses = {
	        UserRepository.class
})
@EnableJpaRepositories(basePackages = "com.cas.ws.io.repositories")
@EntityScan(basePackages = "com.cas.ws.io.entities")
public class CasBackendApplication extends SpringBootServletInitializer{
	public static void main(String[] args) {
		SpringApplication.run(CasBackendApplication.class, args);
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	    public SpringApplicationContext springApplicationContext() {
	        return new SpringApplicationContext();
	    }
	 
	 @Bean(name="AppProperties")
	    public AppProperties appProperities() {
	        return new AppProperties();
	    }
}

