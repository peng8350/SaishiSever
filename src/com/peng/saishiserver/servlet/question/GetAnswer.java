package com.peng.saishiserver.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.AnswerInfo;
import com.peng.saishiserver.entity.QuestionInfo;
import com.peng.saishiserver.service.CaheListener;
import com.peng.saishiserver.utils.GzipUtilities;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

/**
 * Servlet implementation class GetAnswer
 */
@WebServlet("/GetAnswer")
public class GetAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetAnswer() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 通過問題的id來獲取所有的回復
	 * 
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = null;
		if (GzipUtilities.isGzipSupported(request)
				&& !GzipUtilities.isGzipDisabled(request)) {
			pw = GzipUtilities.getGzipWriter(response);
			response.setHeader("Content-Encoding", "gzip");
		} else {
			pw = response.getWriter();
		}
		final String id = request.getParameter("id");// 問題的 id
		final DaoProcessor processor = DaoFactory.createProcessor();

		final String sql = "select * from Question_Answer where questionid = ?";
		java.util.List<AnswerInfo> list =processor.getListBean(sql,
				new Object[] { Integer.parseInt(id) },
				AnswerInfo.class);

		pw.print(JSONArray.fromObject(list));

		
		pw.close();
	}

}
