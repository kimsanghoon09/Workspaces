package com.sh.member.model.dto;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * DTO Data Transfer Object 
 * VO Value Object
 * Entity클래스 - 테이블의 구조와 동일한 객체(Member객체가 하나의 레코드와 동일)
 * Domain 업무로직관련 클래스
 *
 */
public class Member {
	private String id;
	private String name;
	private String gender; // jdbc에는 char타입전용메소드가 없다. 
	private Date birthday;
	private String email;
	private int point;
	private Timestamp regDate;
	
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Member(String id, String name, String gender, Date birthday, String email, int point, Timestamp regDate) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.point = point;
		this.regDate = regDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public Timestamp getRegDate() {
		return regDate;
	}

	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Member [id=" + id + ", name=" + name + ", gender=" + gender + ", birthday=" + birthday + ", email="
				+ email + ", point=" + point + ", regDate=" + regDate + "]";
	}
	
	
	
	
}
