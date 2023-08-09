package com.sh.uml.association.aggregation;

public class Book {
	private BookInfo bookInfo;
	private int id;				// 관리번호
	private boolean isRented;	// 대여여부
	
	public Book(BookInfo bookInfo, int id, boolean isRented) {
		super();
		this.bookInfo = bookInfo;
		this.id = id;
		this.isRented = isRented;
	}

	@Override
	public String toString() {
		return "Book [bookInfo=" + bookInfo + ", id=" + id + ", isRented=" + isRented + "]";
	}
}
