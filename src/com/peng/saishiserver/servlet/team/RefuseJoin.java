package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class RefuseJoin
 */
@WebServlet("/RefuseJoin")
public class RefuseJoin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RefuseJoin() {
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
		{
			// TODO Auto-generated method stub
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			String uid = request.getParameter("uid");
			String pid = request.getParameter("pid");
			String groupid = request.getParameter("groupid");
			DaoProcessor processor = new DaoProcessor();
			TeamInfo team = (TeamInfo) processor.getBean("select * from TeamInfo where groupid = ?", new Object[] { groupid },
					TeamInfo.class);
			String teamname = team.getName();
			String username = (String) processor.getValue("select  username from User where id = ?", new Object[] { pid });
			int picid = team.getId();
			String time = MyDateUtils.getNowTime2();
			Responser responser = new Responser();
			if (processor.Update("update Joingroup set time = ?,content = ?,status =2 where uid = ? and pid = ?", new Object[] {MyDateUtils.getNowTime2(), "你拒绝了 "+username+" 的加入", uid, pid})) {
				processor.Update(
						"insert into Joingroup (uid,pid,content,status,time,groupid,teamname,picid) values (?,?,?,?,?,?,?,?)",
						new Object[] { pid, uid, "你被群主拒绝进入", 2, time, groupid, teamname, picid });
				processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[]{pid});
				responser.SendOk(pw);
			} else {
				responser.SendFailed(pw);
			}
			
			pw.close();
		}

	}

}
