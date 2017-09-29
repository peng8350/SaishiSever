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
 * Servlet implementation class AnswerQuestion
 */
@WebServlet("/AnswerQuestion")
public class AnswerQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AnswerQuestion() {
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
	}

	/**
	 * 回答一個問題的添加請求
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();

		String userid = request.getParameter("userid");
		String username = request.getParameter("username");
		String content = request.getParameter("content");
		String questionid = request.getParameter("questionid");
		String time = MyDateUtils.getNowTime2();
		DaoProcessor processor = DaoFactory.createProcessor();
		String sql = "insert into Question_Answer (username,time,content,questionid,userid) values(?,?,?,?,?)";
		
		boolean success = processor.Update(sql, new Object[] { username, time,
				content, questionid, userid});
		Responser responser = new Responser();
		if (success) {
			responser.SendOk(pw);
		} else {
			responser.SendFailed(pw);

		}
		
		pw.close();
	}
}
