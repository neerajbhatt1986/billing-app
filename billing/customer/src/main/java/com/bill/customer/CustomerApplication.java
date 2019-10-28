package com.bill.customer;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CustomerApplication {

	private static Logger logger = LoggerFactory.getLogger(CustomerApplication.class);

	@Value("${mailjet.api.key}")
	private String mailjetApiKey;
	@Value("${mailjet.api.secret}")
	private String mailjetApiSecret;

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Bean
	MailjetClient mailjetClient(){
		ClientOptions options = new ClientOptions("v3.1");
		return new MailjetClient(mailjetApiKey, mailjetApiSecret, options);
	}

	public static void main(String[] args) {
		SpringApplication.run(CustomerApplication.class, args);
	}

}
