package com.sh.app.pet;

import org.springframework.stereotype.Component;

@Component
public class Cat implements Pet {

	public void helloPerson() {
		System.out.println("야옹~!");
	}

}
