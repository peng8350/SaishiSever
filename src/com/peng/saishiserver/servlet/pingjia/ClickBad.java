package com.peng.saishiserver.servlet.pingjia;

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
 * 评价不好
 * 传入 id,评价的id
 */
@WebServlet("/ClickBad")
public class ClickBad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClickBad() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		PrintWriter pw = response.getWriter();
		String id = request.getParameter("id");
		String userid= request.getParameter("userid");
		String sql = "update pingjia set bad = concat(bad,?) where id = ?";
		DaoProcessor processor = DaoFactory.createProcessor();
		boolean success= processor.Update(sql, new String[]{userid+" ",id});
		Responser responser = new Responser();
		if (success) {
			responser.SendOk(pw);
		}
		else{
			responser.SendFailed(pw);
		}
		
		pw.close();
	}

}
