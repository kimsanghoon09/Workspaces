package com.sh.app.user;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class UserConfig {

	/**
	 * id : 메소드명 userController
	 * class : 반환타입 UserController
	 */
	@Bean
	@Scope
	@Lazy
	public UserController userController() {
		System.out.println("UserController빈을 생성합니다...");
		return new UserController(userService());
	}
	/**
	 * id : userService
	 * class : com.sh.app.user.UserService
	 */
	@Bean
	public UserService userService() {
		System.out.println("UserService빈을 생성합니다...");
		return new UserServiceImpl();
	}
}
