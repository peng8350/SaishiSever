package com.peng.saishiserver.servlet.question;

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
import com.peng.saishiserver.entity.QuestionInfo;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class DelQuestion
 */
@WebServlet("/DelQuestion")
public class DelQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DelQuestion() {
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
	 * 用户删除自己的提问
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
		String params = request.getParameter("params");
		String sql = "delete from Question where id  in (" + params + ")";

		DaoProcessor processor = DaoFactory.createProcessor();
		
		Responser responser = new Responser();
		if (processor.Update(sql, null)) {
			responser.SendOk(pw);
		} else {
			responser.SendFailed(pw);

		}

		pw.close();
	}

}
