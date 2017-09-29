package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.JoinGroup;
import com.peng.saishiserver.utils.GzipUtilities;
import com.peng.saishiserver.utils.MyDateUtils;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getApplys
 */
@WebServlet("/getApplys")
public class getApplys extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getApplys() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		String id = request.getParameter("id");
		String sql1 = "select * from Joingroup where uid = ?";
		
		DaoProcessor processor = new DaoProcessor();
		//根据给定的id，获取当前用户的所有群的
		List<JoinGroup> param =processor.getListBean(sql1, new Object[]{id}, JoinGroup.class);
		pw.println(JSONArray.fromObject(param));
		
		pw.close();
	}

}
