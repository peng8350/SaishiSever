package com.peng.saishiserver.servlet.match;

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
import com.peng.saishiserver.entity.MatchInfo;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 得到所有的大赛信息 不用传入参数
 */
@WebServlet("/GetAllMatter")
public class GetAllMatter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAllMatter() {
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
		DaoProcessor processor = DaoFactory.createProcessor();
		List<MatchInfo> list = processor.getListBean("SELECT * FROM MatchInfo order by type1", null, MatchInfo.class);
		pw.println(JSONArray.fromObject(list));
		
		pw.close();
	}

}
