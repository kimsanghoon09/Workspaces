package com.sh.member.model.dto;

import java.sql.Date;

public class Address {
	private int no;
//	private String memberId;
	private Member member;
	private String address;
	private boolean defaultAddr;
	private Date regDate;
	
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Address(int no, Member member, String address, boolean defaultAddr, Date regDate) {
		super();
		this.no = no;
		this.member = member;
		this.address = address;
		this.defaultAddr = defaultAddr;
		this.regDate = regDate;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isDefaultAddr() {
		return defaultAddr;
	}

	public void setDefaultAddr(boolean defaultAddr) {
		this.defaultAddr = defaultAddr;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Address [no=" + no + ", member=" + member + ", address=" + address + ", defaultAddr=" + defaultAddr
				+ ", regDate=" + regDate + "]";
	}
	
	
	
	
	
}
