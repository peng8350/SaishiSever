package com.peng.saishiserver.entity;

import java.util.UUID;

public class Pingjia {
	private int id;
	private String name;
	private String picUrl;
	private String good;
	private String bad;
	private String content;
	private String time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Pingjia(int id, String name, String picUrl, String good, String bad, String content, String time) {
		super();
		this.id = id;
		this.name = name;
		this.picUrl = picUrl;
		this.good = good;
		this.bad = bad;
		this.content = content;
		this.time = time;
	}

	public Pingjia() {
		// TODO Auto-generated constructor stub
	}

}
