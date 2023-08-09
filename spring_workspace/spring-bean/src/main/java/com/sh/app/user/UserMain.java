package com.sh.app.user;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class UserMain {
	
	/**
	 * @Configuration 어노테이션을 가진 빈등록 클래스
	 */
	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(UserConfig.class);
		System.out.println("===== 스프링 컨테이너 초기화 완료 =====");
		
		UserController userController = context.getBean(UserController.class);
		userController.insertUser(); // 의존 주입을 못받았다면 null point exception발생.
	}
}
