package com.sh.app.member.model.dto;

public class Member {
	private String id;
	private String password;
	public Member() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Member(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Member [id=" + id + ", password=" + password + "]";
	}
}
