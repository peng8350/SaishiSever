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
 * Servlet implementation class UpdatePswd
 */
@WebServlet("/UpdatePswd")
public class UpdatePswd extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdatePswd() {
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
		String old_pswd = request.getParameter("old");
		String new_pswd = request.getParameter("new");
		String sql = "update User set pswd = ? where user = ? and pswd=?";
		DaoProcessor processor = new DaoProcessor();
		Responser responser = new Responser();
		
		if(processor.Update(sql, new Object[] { new_pswd, user,old_pswd})){
			//修改成功
			responser.SendOk(pw);
		}
		else{
			responser.SendFailed(pw);
		}
		
		pw.close();
	}

}
