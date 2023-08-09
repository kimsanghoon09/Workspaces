package com.sh.app.news.entity;

public class News {
	private int id;
	private String content;
	public News() {
		super();
		// TODO Auto-generated constructor stub
	}
	public News(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "News [id=" + id + ", content=" + content + "]";
	}
}
