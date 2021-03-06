package com.peng.saishiserver.servlet.question;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.sun.java.swing.plaf.gtk.GTKConstants.Orientation;

/**
 * Servlet implementation class VisitQuestion
 */
@WebServlet("/VisitQuestion")
public class VisitQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VisitQuestion() {
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
		String id =request.getParameter("id");
		
		String sql = "update Question set visit = visit +1 where id   = ?";
		DaoProcessor processor = DaoFactory.createProcessor();
		processor.Update(sql, new Object[]{id});
		pw.close();
	}

}
