package com.sh.ajax.celeb.model.vo;

public class Celeb {
	private int no;
	private String name;
	private String profile; // 프로필사진명
	private CelebType celebType;
	
	public Celeb() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Celeb(int no, String name, String profile, CelebType celebType) {
		super();
		this.no = no;
		this.name = name;
		this.profile = profile;
		this.celebType = celebType;
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

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public CelebType getCelebType() {
		return celebType;
	}

	public void setCelebType(CelebType celebType) {
		this.celebType = celebType;
	}

	@Override
	public String toString() {
		return "Celeb [no=" + no + ", name=" + name + ", profile=" + profile + ", celebType=" + celebType + "]";
	}
	
	
	
}
