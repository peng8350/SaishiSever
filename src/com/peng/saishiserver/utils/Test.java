package com.peng.saishiserver.utils;

import java.util.ArrayList;
import java.util.List;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.JoinGroup;
import com.peng.saishiserver.entity.MatchInfo;
import com.peng.saishiserver.entity.SearchInfo;
import com.peng.saishiserver.entity.TeamInfo;

public class Test {

	public static void main(String[] args) {
		DaoProcessor processor = DaoFactory.createProcessor();
		long time = System.currentTimeMillis();
	}

	/**
	 * 得到所有比赛详情
	 */
	public static List<MatchInfo> getAllMatches(DaoProcessor processor) {

		return processor.getListBean(
				"SELECT * FROM MatchInfo order by id desc", null,
				MatchInfo.class);
	}

	/**
	 * 得到用户的队伍
	 */
	public static List<List<TeamInfo>> getTeamInfo(DaoProcessor processor,
			String id) {
		List<List<TeamInfo>> teamsList = new ArrayList<>();
		List<TeamInfo> list1 = null;
		List<TeamInfo> list2 = null;
		List<Integer> ids1 = (List<Integer>) processor.getValues(
				"select team_id from User_team where id = ? and type =1",
				new Object[] { id });
		List<Integer> ids2 = (List<Integer>) processor.getValues(
				"select team_id from User_team where id = ? and type =2",
				new Object[] { id });

		if (ids1.size() == 0) {
			list1 = new ArrayList<>();
		} else {
			String temp = "";
			for (int i = 0; i < ids1.size(); i++) {
				if (i == ids1.size() - 1) {
					temp += ids1.get(i);
				} else
					temp += ids1.get(i) + ",";
			}
			String sql = "select * from TeamInfo where id in(" + temp + ")";
			list1 = processor.getListBean(sql, null, TeamInfo.class);
		}
		if (ids2.size() == 0) {
			list2 = new ArrayList<>();
		} else {
			String temp = "";
			for (int i = 0; i < ids2.size(); i++) {
				if (i == ids2.size() - 1) {
					temp += ids2.get(i);
				} else
					temp += ids2.get(i) + ",";
			}
			String sql = "select * from TeamInfo where id in(" + temp + ")";
			list2 = processor.getListBean(sql, null, TeamInfo.class);
		}
		teamsList.add(list1);
		teamsList.add(list2);

		return teamsList;
	}

	/**
	 * 得到用户的收藏
	 */
	public static List<MatchInfo> getShoucang(DaoProcessor processor, String id) {
		List<MatchInfo> list = null;
		List<Integer> ids = (List<Integer>) processor.getValues(
				"select match_id from User_Shoucang where id = ?",
				new Object[] { id });
		if (ids.size() == 0) {
			list = new ArrayList<>();
		} else {
			String temp = "";
			for (int i = 0; i < ids.size(); i++) {
				if (i == ids.size() - 1) {
					temp += ids.get(i);
				} else
					temp += ids.get(i) + ",";
			}
			String sql = "select * from MatchInfo where id in(" + temp + ")";
			return processor.getListBean(sql, null, MatchInfo.class);
		}
		return null;
	}
}
