package com.sh.uml.association;

/**
 * 방향성에 따른 연관관계 구분
 * 
 * 단방향 연관관계 
 * - Person은 Phone을 가지고 있고, Phone은 Person은 가지고 있지않다.
 * 
 * 양방향 연관관계
 * - Person은 Home을 가지고 있고, Home도 Person을 가지고 있다. 
 *
 */
public class Person {
	private String id;
	private String name;
	private Phone phone;
	private Home home;
}
