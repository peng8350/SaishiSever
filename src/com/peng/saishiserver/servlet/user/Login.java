package com.peng.saishiserver.servlet.user;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.peng.saishiserver.config.ServerConfig;
import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.MatchInfo;
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.entity.User;
import com.peng.saishiserver.utils.GzipUtilities;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class Login 　登陆的 servlet 传入user,pswd
 * 假如用户登陆成功,以{result:ok,user:javabean}对象的形式返回客户端
 * 
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println(ServerConfig.radio_pos);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = null;
		if (GzipUtilities.isGzipSupported(request)
				&& !GzipUtilities.isGzipDisabled(request)) {
			pw = GzipUtilities.getGzipWriter(response);
			response.setHeader("Content-Encoding", "gzip");
		} else {
			pw = response.getWriter();
		}
		// 用户账号
		String user = request.getParameter("user");
		// 用户密码
		String pswd = request.getParameter("pswd");
		boolean first = request.getParameter("first").equals("true");
		Responser responser = new Responser();
		final DaoProcessor processor = DaoFactory.createProcessor();
		JSONObject obj = new JSONObject();
		if (isLogin(user, pswd, processor)) {
			// 登陆成功
			final User re_user = (User) processor.getBean(

			"select * from User where user = ?", new Object[] { user },
					User.class);
			responser.AddOk(obj);
			String shoucangs = getShoucang(processor, re_user.getId() + "");
			List<List<TeamInfo>> myteams = getTeamInfo(processor,
					re_user.getId() + "");
			if (first) {
				List<MatchInfo> matches = getAllMatches(processor);

				obj.put("matches", matches);
			}
			
			obj.put("shoucangs", shoucangs == null ? new ArrayList<MatchInfo>()
					: shoucangs);
			obj.put("team1", myteams.get(0) == null ? new ArrayList<TeamInfo>()
					: myteams.get(0));
			obj.put("team2", myteams.get(1) == null ? new ArrayList<TeamInfo>()
					: myteams.get(1));
			obj.put("user", re_user);
		} else {
			// 登陆失败，账号和密码都是错误的
			responser.SendFailed(pw);
		}
		pw.write(obj.toString());
		pw.close();
	}
	
	 // 压缩   
	 public static String compress(String str) throws IOException {   
	    if (str == null || str.length() == 0) {   
	     return str;   
	   }   
	    ByteArrayOutputStream out = new ByteArrayOutputStream();   
	   GZIPOutputStream gzip = new GZIPOutputStream(out);   
	    gzip.write(str.getBytes());   
	    gzip.close();   
	   return out.toString("ISO-8859-1");   
	  }   
	  

	/**
	 * 得到所有比赛详情
	 */
	public List<MatchInfo> getAllMatches(DaoProcessor processor) {

		return processor.getListBean(
				"SELECT * FROM MatchInfo order by id desc", null,
				MatchInfo.class);
	}

	/**
	 * 得到用户的队伍
	 */
	public List<List<TeamInfo>> getTeamInfo(DaoProcessor processor, String id) {
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
			String sql = "select * from TeamInfo where id in(" + temp
					+ ") order by id desc";
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
	public String getShoucang(DaoProcessor processor, String id) {
		List<Integer> ids = (List<Integer>) processor.getValues(
				"select match_id from User_Shoucang where id = ?",
				new Object[] { id });
		if (ids.size() == 0) {
			return "";
		}
		String re_str = "";
		for (int i = 0; i < ids.size(); i++) {
			if (i == ids.size() - 1) {
				re_str += ids.get(i);
			} else
				re_str += ids.get(i) + ",";
		}
		return re_str;
	}

	/**
	 * 判断是否密码和账号正确
	 */
	public boolean isLogin(String user, String pswd, DaoProcessor processor) {
		boolean flag = false;
		String sql = "select pswd from User where user = ?";

		String pswd_last = (String) processor.getValue(sql,
				new Object[] { user });
		if (pswd_last != null && pswd_last.equals(pswd)) {
			flag = true;
		}
		return flag;
	}
}
