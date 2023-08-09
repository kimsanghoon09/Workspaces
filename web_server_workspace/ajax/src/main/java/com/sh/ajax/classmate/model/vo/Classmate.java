package com.sh.ajax.classmate.model.vo;

public class Classmate {
	private String name;

	public Classmate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Classmate(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Classmate [name=" + name + "]";
	}
	
	
}
