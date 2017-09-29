package com.peng.saishiserver.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class CreateQuestion
 */
@WebServlet("/CreateQuestion")
public class CreateQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateQuestion() {
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String time = MyDateUtils.getNowTime2();
		String matchid = request.getParameter("matchid");
		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String matchname= request.getParameter("matchname");
		DaoProcessor processor = DaoFactory.createProcessor();
		String sql = "insert into Question (title,content,time,userid,username,visit,matchid,matchName) values(?,?,?,?,?,0,?,?)";
		Responser responser = new Responser();

		boolean success = processor.Update(sql, new Object[] { title, content,
				time, userid, username, matchid,matchname});
		if (success) {
			responser.SendOk(pw);
		} else {
			responser.SendFailed(pw);
		}

		

		pw.close();
	}

}
