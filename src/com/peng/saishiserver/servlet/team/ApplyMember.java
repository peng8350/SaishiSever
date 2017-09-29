package com.peng.saishiserver.servlet.team;

import java.awt.image.renderable.RenderableImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.MidiDevice.Info;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.JoinGroup;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ApplyMember
 */
@WebServlet("/ApplyMember")
public class ApplyMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ApplyMember() {
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
		String uid = request.getParameter("uid");
		String pid = request.getParameter("pid");
		String content = request.getParameter("content");
		String teamname = request.getParameter("teamname");
		String picid = request.getParameter("picid");
		String target = request.getParameter("target");
		String groupid = request.getParameter("groupid");
		String time = MyDateUtils.getNowTime2();
		String sql = "insert into Joingroup (target,uid,pid,content,time,teamname,groupid,picid) values(?,?,?,?,?,?,?,?)";
		DaoProcessor processor = new DaoProcessor();
		int user_id = 0;
		user_id = (int) processor.getValue("select id from User where user = ?", new Object[] { uid });
		JoinGroup join_info = (JoinGroup) processor.getBean("select * from Joingroup where uid = ? and pid = ? and groupid = ? and status=1",
				new Object[] { user_id, pid,groupid}, JoinGroup.class);

		Responser responser = new Responser();
		if (join_info == null ) {
			if (processor.Update(sql, new Object[] { target, user_id, pid, content, MyDateUtils.getNowTime2(), teamname,
					groupid, picid })) {
			
				responser.SendOk(pw);
			} else {
				responser.SendFailed(pw);
			}
		} else {
			processor
			.Update("update Joingroup set content = ?,status=1,time=?,picid = ? where uid = ? and pid = ? and groupid = ? and target !=0",
					new Object[] { content, time, picid, user_id,
							pid, groupid });
			if (join_info.getStatus()==1&&join_info.getUid()==user_id&&join_info.getPid()==Integer.parseInt(pid)) {
				JSONObject send_obj = new JSONObject();

				send_obj.put("result", "你已经发送申请给队长了");
				pw.println(send_obj);
			}
			else{
				// 假如已经存在

				JSONObject send_obj = new JSONObject();
				send_obj.put("result", "ok");
				pw.println(send_obj);
			}
		}
		processor.Update("update User set unreadcount = unreadcount +1 where id = ?", new Object[] { user_id });
		
		pw.close();
	}

}
