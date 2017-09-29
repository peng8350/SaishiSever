package com.peng.saishiserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class Shoucang
 */
@WebServlet("/Shoucang")
public class Shoucang extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Shoucang() {
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
		String id = request.getParameter("id");
		String match_id = request.getParameter("target");
		String isadd = request.getParameter("add");
		DaoProcessor processor = DaoFactory.createProcessor();
		String sql = isadd.equals("true") ? "insert into User_Shoucang values(?,?)"
				: "delete from User_Shoucang where id = ? and match_id = ?";
		Responser responser = new Responser();
		if (processor.Update(sql, new Object[] { id, match_id })) {
			responser.SendOk(pw);
		} else {
			responser.SendFailed(pw);
		}
		
		pw.close();
	}

}
