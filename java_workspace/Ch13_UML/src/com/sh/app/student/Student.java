package com.sh.app.student;

import java.util.Map;

public class Student {
	private int no;
	private String name;
	private String phone;
	private Map<String, Integer> scoreMap;
	
	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int no, String name, String phone, Map<String, Integer> scoreMap) {
		super();
		this.no = no;
		this.name = name;
		this.phone = phone;
		this.scoreMap = scoreMap;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Map<String, Integer> getScoreMap() {
		return scoreMap;
	}

	public void setScoreMap(Map<String, Integer> scoreMap) {
		this.scoreMap = scoreMap;
	}

	@Override
	public String toString() {
		return "Student [no=" + no + ", name=" + name + ", phone=" + phone + ", scoreMap=" + scoreMap + "]";
	}
	
}
