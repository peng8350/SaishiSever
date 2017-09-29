package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.entity.User;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;
import com.sun.media.sound.SoftSynthesizer;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class AgreeJoin
 */
@WebServlet("/AgreeJoin")
public class AgreeJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AgreeJoin() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String groupid = request.getParameter("groupid");
		String members_param = request.getParameter("members");
		DaoProcessor processor = new DaoProcessor();
		TeamInfo info = (TeamInfo) processor.getBean("select * from TeamInfo where groupid = ?", new Object[] { groupid },
				TeamInfo.class);
		String teamname = info.getName();
		int picid = info.getId();
		String time = MyDateUtils.getNowTime2();
		List<Integer> id_list = (List<Integer>) processor
				.getValues("select id from User where user in (" + members_param + ")", null);
		String sql = "update Joingroup set status=0,time=?,content = ? where uid = ? and pid = ? and status = 1 and id = ? ";
		String insert_sql = "insert into Joingroup (uid,pid,content,status,time,groupid,teamname,picid) values (?,?,?,?,?,?,?,?)";
		String username = (String) processor.getValue("select  username from User where id = ?", new Object[] { pid });
		TeamInfo team = (TeamInfo) processor.getBean("select * from TeamInfo where groupid = ?", new Object[] { groupid },
				TeamInfo.class);
		Responser responser = new Responser();
		if (team.getNeedperson()==team.getNowperson()) {
			JSONObject res_obj = new JSONObject();
			res_obj.put("result", "你的队伍要求已经满人了!!");
			pw.println(res_obj);
		}
		else if (processor.Update(sql, new Object[] { MyDateUtils.getNowTime2(), "你已经接受 " + username + " 的加入", uid, pid,id})) {
			// 给所有在群的用户修改增加他们的记录
			for (int i : id_list) {
				if (i == Integer.parseInt(uid)) {
					continue;

				}
				processor.Update(insert_sql,
						new Object[] { i, pid, username + "已经加入", 0, time, groupid, teamname, picid });
				processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[] { i });

			}
			// 更新用户的队伍关系表
			processor.Update("insert into User_team values(?,?,2)", new Object[] { pid, info.getId() });
			processor.Update("update TeamInfo set nowperson = nowperson +1 where groupid = ?",
					new Object[] { groupid });
			responser.SendOk(pw);
		} else {

			responser.SendFailed(pw);
		}

		
		pw.close();
	}

}
