package com.peng.saishiserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class UpdateUser
 */
@WebServlet("/UpdateUser")
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateUser() {
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
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String school = request.getParameter("school");
		String major = request.getParameter("major");
		String phone = request.getParameter("phone");
		int age = Integer.parseInt(request.getParameter("age"));
		String sex = request.getParameter("sex");
		String xueli = request.getParameter("xueli");
		String place = request.getParameter("place");
		String intro = request.getParameter("intro");
		String hobby = request.getParameter("hobby");

		String sql = "update User set username = ?,school=?,major=?,phone=?,age=?,sex=?,xueli=?,place=?,intro=?,hobby=? where user = ?";
		Responser resonse = new Responser();
		DaoProcessor processor = new DaoProcessor();
		if(processor.Update(sql, new Object[] { name, school, major, phone, age, sex, xueli, place, intro, hobby, user })){
			
			resonse.SendOk(pw);
		}
		else{
			resonse.SendFailed(pw);
		}
		
		pw.close();
	}

}
