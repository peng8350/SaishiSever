package com.peng.saishiserver.servlet.team;

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
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getAllTeam
 */
@WebServlet("/getAllTeam")
public class getAllTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllTeam() {
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
		String index =request.getParameter("index");
		String limit = request.getParameter("limit");
		String id = request.getParameter("lastid");
		String sql = "select * from TeamInfo  order by id desc limit ?,?";
		DaoProcessor processor = DaoFactory.createProcessor();
		List<TeamInfo> list = null;
		if (id==null) {
			list=processor.getListBean(sql,
					new Object[] { Integer.parseInt(index),Integer.parseInt(limit==null?10+"":limit)}, TeamInfo.class);
		}
		else{
			list=processor.getListBean("select * from TeamInfo where id < ?  order by id desc limit 0,?",
					new Object[] { Integer.parseInt(id),Integer.parseInt(limit==null?10+"":limit)}, TeamInfo.class);
		}
		pw.println(JSONArray.fromObject(list));
		
		pw.close();
	}

}
