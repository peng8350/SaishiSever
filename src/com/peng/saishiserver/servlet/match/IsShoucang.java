package com.peng.saishiserver.servlet.match;

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

import net.sf.json.JSONObject;

/**
 * Servlet implementation class IsShoucang
 */
@WebServlet("/IsShoucang")
public class IsShoucang extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IsShoucang() {
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
		String user_id = request.getParameter("userid");
		String match_id = request.getParameter("matchid");
		DaoProcessor processor = DaoFactory.createProcessor();
		String sql = "select  * from User_Shoucang where id = ? and match_id  = ?";
		int count = processor.getCount(sql, new Object[] { user_id, match_id });
		JSONObject send_obj = new JSONObject();
		if (count == 1) {
			
			send_obj.put("have", "true");
		} else {
			
			send_obj.put("have", "false");
		}
		pw.println(send_obj);
		
		pw.close();

	}

}
