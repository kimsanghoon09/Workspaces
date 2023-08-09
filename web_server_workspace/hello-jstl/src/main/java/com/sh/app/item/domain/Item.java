package com.sh.app.item.domain;

import java.util.Objects;

public class Item {
	private int no;
	private String name;
	private int price;
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Item(int no, String name, int price) {
		super();
		this.no = no;
		this.name = name;
		this.price = price;
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

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(name, no, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(name, other.name) && no == other.no && price == other.price;
	}

	@Override
	public String toString() {
		return "Item [no=" + no + ", name=" + name + ", price=" + price + "]";
	}	
}
