package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;

import javax.jws.soap.SOAPBinding.Use;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.User;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getTeamMember
 */
@WebServlet("/getTeamMember")
public class getTeamMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getTeamMember() {
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
		PrintWriter pw=null;
		if (GzipUtilities.isGzipSupported(request)
				&& !GzipUtilities.isGzipDisabled(request)) {
			pw = GzipUtilities.getGzipWriter(response);
			response.setHeader("Content-Encoding", "gzip");
		} else {
			pw = response.getWriter();
		}
		String param = request.getParameter("members");
		DaoProcessor processor = new DaoProcessor();
		pw.print(JSONArray.fromObject(
				processor.getListBean("select * from User where user in(" + param + ") or id in (?)", new Object[]{param}, User.class)));
		;
		
		pw.close();
	}

}
