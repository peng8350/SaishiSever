package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.entity.User;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class ExitTeam
 */
@WebServlet("/ExitTeam")
public class ExitTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExitTeam() {
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		DaoProcessor processor = DaoFactory.createProcessor();

		PrintWriter pw = response.getWriter();
		String teamid = request.getParameter("teamid");
		String exitid = request.getParameter("id");
		String member_param = request.getParameter("member");
		User user = (User) processor.getBean("select * from User where id = ?", new String[] { exitid }, User.class);
		TeamInfo team = (TeamInfo) processor.getBean("select * from TeamInfo where id = ? ", new String[] { teamid },
				TeamInfo.class);
		String time = MyDateUtils.getNowTime2();
		List<Integer> ids = (List<Integer>) processor.getValues("select id from User where user in (" + member_param + ")",
				null);
		// 对JOingroup表进行操作
		for (int id : ids) {
			if (id!=Integer.parseInt(exitid)) {
				processor.Update(
						"insert into Joingroup (status,uid,pid,content,time,teamname,groupid,picid) values(?,?,?,?,?,?,?,?)",
						new Object[] { 0,id, exitid, user.getUsername() + " 已经退出了 ", time, team.getName(), team.getGroupid(),
								team.getId() });
				processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[]{id});
			}
	
		}

		// 对User_team操作
		Responser reponser = new Responser();
		if (processor.Update("delete from User_team where id = ? and team_id = ?", new String[] { exitid, teamid })) {
			reponser.SendOk(pw);
			processor.Update("update TeamInfo set nowperson = nowperson -1 where groupid = ?", new Object[]{team.getGroupid()});
		} else {
			reponser.SendFailed(pw);
		}
		

		pw.close();
	}

}
