package com.peng.saishiserver.entity;


public class TeamInfo {
	private int id;
	private int matchid;
	private long groupid;
	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public long getGroupid() {
		return groupid;
	}

	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}

	public int getMatchid() {
		return matchid;
	}

	public void setMatchid(int matchid) {
		this.matchid = matchid;
	}

	private String name;
	private String matchName;
	private int nowperson;
	private int needperson;
	private String time;
	private String intro;
	private String yaoqiu;
	private String plan;
	private String target;
	private String type;
	private String tags;
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}


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

	public String getMatchName() {
		return matchName;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public int getNowperson() {
		return nowperson;
	}

	public void setNowperson(int nowperson) {
		this.nowperson = nowperson;
	}

	public int getNeedperson() {
		return needperson;
	}

	public void setNeedperson(int needperson) {
		this.needperson = needperson;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}


	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getYaoqiu() {
		return yaoqiu;
	}

	public void setYaoqiu(String yaoqiu) {
		this.yaoqiu = yaoqiu;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public TeamInfo(int id, String name, String matchName, int nowperson, int needperson, String time, 
			String intro, String yaoqiu, String plan, String target, String type,String tags) {
		super();
		this.id = id;
		this.name = name;
		this.matchName = matchName;
		this.nowperson = nowperson;
		this.needperson = needperson;
		this.time = time;
		this.intro = intro;
		this.yaoqiu = yaoqiu;
		this.plan = plan;
		this.target = target;
		this.type = type;
		this.tags= tags;
	}

	public TeamInfo() {
		// TODO Auto-generated constructor stub
	}
}
