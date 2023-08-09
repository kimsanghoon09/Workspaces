package com.sh.app.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class Person {
	/**
	 * Pet타입의 빈(Pet을 구현한 빈을 조회해서 의존주입)
	 * 
	 * - field레벨
	 * - method 레벨
	 * - constructor 레벨 
	 */
	@Autowired
	@Qualifier("cat") // bean의 이름을 직접작성해서 사용
	private Pet pet;
	
	public Person() {
		System.out.println("Person 객체생성!!");
		System.out.println(this.pet); 
	}
	
	public void helloPet() {
		System.out.println(this.pet);
		this.pet.helloPerson();
	}
}