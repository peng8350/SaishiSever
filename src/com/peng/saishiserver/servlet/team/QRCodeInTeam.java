package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;
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

import net.sf.json.JSONObject;

/**
 * Servlet implementation class QRCodeInTeam
 */
@WebServlet("/QRCodeInTeam")
public class QRCodeInTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QRCodeInTeam() {
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		String uid = request.getParameter("uid");
		String groupid = request.getParameter("groupid");
		String members_param = request.getParameter("members");
		DaoProcessor processor = new DaoProcessor();
		TeamInfo info = (TeamInfo) processor.getBean("select * from TeamInfo where groupid = ?", new Object[] { groupid },
				TeamInfo.class);
		User user = (User) processor.getBean("select * from User where user = ?", new Object[] { uid }, User.class);
		String teamname = info.getName();
		int picid = info.getId();
		String time = MyDateUtils.getNowTime2();
		List<Integer> id_list = (List<Integer>) processor
				.getValues("select id from User where user in (" + members_param + ")", null);
		String insert_sql = "insert into Joingroup (uid,pid,content,status,time,groupid,teamname,picid) values (?,?,?,?,?,?,?,?)";
		String username = user.getUsername();
		Responser responser = new Responser();
		id_list.add(user.getId());
		for (int id : id_list) {
				processor.Update(insert_sql,
						new Object[] { id, user.getId(), (user.getId()==id?"你":username) + "通过二维码加入队伍", 0, time, groupid, teamname, picid });
				processor.Update("update User set unreadcount=unreadcount + 1 where id = ?", new String[] { id+"" });
		}
		processor.Update("update TeamInfo set nowperson = nowperson  +1 where groupid = ?", new Object[] { groupid });
		processor.Update("insert into User_team values(?,?,2)", new Object[] { user.getId(), info.getId() });
		responser.SendOk(pw);
		
		pw.close();
	}

}
