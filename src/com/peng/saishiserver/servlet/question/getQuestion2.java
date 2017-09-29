package com.peng.saishiserver.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.QuestionInfo;
import com.peng.saishiserver.service.CaheListener;
import com.peng.saishiserver.utils.GzipUtilities;

/**
 * Servlet implementation class getQuestion2
 */
@WebServlet("/getQuestion2")
public class getQuestion2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getQuestion2() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

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
		final String id = request.getParameter("id");
		final DaoProcessor processor = DaoFactory.createProcessor();
		List<QuestionInfo> list = processor.getListBean(
				"select * from Question where userid = ? order by id desc", new Object[]{Integer.parseInt(id)},
				QuestionInfo.class);
		
		

		pw.println(JSONArray.fromObject(list));
		pw.close();

	}

}
