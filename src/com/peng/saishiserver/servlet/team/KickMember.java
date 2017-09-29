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
import com.sun.xml.internal.ws.wsdl.writer.document.Port;

/**
 * Servlet implementation class KickMember
 */
@WebServlet("/KickMember")
public class KickMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KickMember() {
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
		PrintWriter pw = response.getWriter();

		DaoProcessor processor = DaoFactory.createProcessor();

		String teamid = request.getParameter("teamid");
		String nokickmember_param = request.getParameter("nokickmember");
		String kickmember_param = request.getParameter("kickmember");
		TeamInfo team = (TeamInfo) processor.getBean("select * from TeamInfo where id = ? ", new String[] { teamid },
				TeamInfo.class);
		String time = MyDateUtils.getNowTime2();
		List<Integer> nokick_ids = (List<Integer>) processor
				.getValues("select * from User where user in (" + nokickmember_param + ")", null);// 没有杯T的人的集合
		List<Integer> kick_ids = (List<Integer>) processor
				.getValues("select * from User where user in (" + kickmember_param + ")", null);// 踢了的人的集合
		System.out.println("没有被T的人数"+nokick_ids.size());
		System.out.println("被T的人数"+kick_ids.size());
		for(int i=0;i<kick_ids.size();i++){
			System.out.println("被T的id"+kick_ids.get(i));
		}
		// 对JOingroup表进行操作
		processor.Update("update TeamInfo set nowperson = nowperson - ? where id = ?",
				new Object[] { kick_ids.size(), team.getId() });
		for (int id : kick_ids) {
			processor.Update(
					"insert into Joingroup (status,uid,pid,content,time,teamname,groupid,picid) values(?,?,?,?,?,?,?,?)",
					new Object[] { 0,id, id, "你已经被群主踢出去了 ", time, team.getName(), team.getGroupid(), team.getId() });
			processor.Update("delete from User_team where id = ? and team_id = ?", new String[] { id + "", teamid });
			processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[]{id});
			User user = (User) processor.getBean("select * from User where id = ?", new String[] { id + "" }, User.class);
			for (int i : nokick_ids) {
				processor.Update(
						"insert into Joingroup (status,uid,pid,content,time,teamname,groupid,picid) values(?,?,?,?,?,?,?,?)",
						new Object[] { 0, i, id, user.getUsername() + " 已经被群主踢出去了 ", time, team.getName(),
								team.getGroupid(), team.getId() });
				processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[]{i});
			}
		}

		// 对User_team操作
		Responser reponser = new Responser();
		reponser.SendOk(pw);

		
		pw.close();
	}

}
