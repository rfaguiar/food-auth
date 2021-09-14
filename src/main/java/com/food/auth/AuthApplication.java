package com.food.auth;

import com.food.auth.config.io.Base64ProtocolResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		var application = new SpringApplication(AuthApplication.class);
		application.addListeners(new Base64ProtocolResolver());
		application.run(args);
	}

}
