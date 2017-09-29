package com.peng.saishiserver.entity;

public class SearchInfo {
	private int id;//主键
	private String content;//内容
	private int time;
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
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public SearchInfo(int id, String content, int time) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
	}

	public SearchInfo() {
		// TODO Auto-generated constructor stub
	}
	
}
