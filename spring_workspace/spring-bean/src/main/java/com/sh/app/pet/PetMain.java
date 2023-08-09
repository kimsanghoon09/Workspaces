package com.sh.app.pet;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PetMain {
	
	public static void main(String[] args) {
		String configLocation = "petApplicationContext.xml";
		ApplicationContext context = new ClassPathXmlApplicationContext(configLocation);
		System.out.println("===== 스프링 컨테이너 초기화 완료 =====");
		
		Person person = context.getBean(Person.class);
		person.helloPet();
		
	}
}
