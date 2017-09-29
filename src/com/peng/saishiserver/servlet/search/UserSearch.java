package com.peng.saishiserver.servlet.search;

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
 * Servlet implementation class UserSearch
 */
@WebServlet("/UserSearch")
public class UserSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserSearch() {
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
		String content = request.getParameter("content");
		DaoProcessor processor = DaoFactory.createProcessor();
		Responser repsonser = new Responser();
		int count = processor.getCount("select * from Search where content = ?", new Object[] { content });
		if (count == 1) {
			// 假如已经存在数据
			String sql = "update Search set time = time +1 where content = ?";
			if (processor.Update(sql, new Object[] { content })) {
				repsonser.SendOk(pw);

			} else {
				repsonser.SendFailed(pw);
			}

		} else {
			// 新建
			// time表明次数
			String sql = "insert into Search (content,time) values(?,?)";
			if (processor.Update(sql, new Object[] { content, 1 })) {
				repsonser.SendOk(pw);
			} else {
				repsonser.SendFailed(pw);
			}

		}
		
		pw.close();
	}

}
