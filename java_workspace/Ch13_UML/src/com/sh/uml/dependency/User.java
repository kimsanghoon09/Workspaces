package com.sh.uml.dependency;

/**
 * 의존관계
 * - User가 Item에 의존한다.
 * - User#buy안에서 Item클래스를 지역변수로 참조한다.
 *
 */
public class User {
	private String username;
	
	public void buy(Item item) {
		System.out.println(this.username + "이/가 " + item + "을 구매한다.");
	}
}
