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

/**
 * Servlet implementation class getMatchVisit
 */
@WebServlet("/getMatchVisit")
public class getMatchVisit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getMatchVisit() {
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
		PrintWriter pw=response.getWriter();
		
		String id = request.getParameter("id");
		
		DaoProcessor processor = DaoFactory.createProcessor();
		
		int person = (int) processor.getValue("select visitPerson from MatchInfo where id = ?", new Object[]{id});
		
		pw.print(person);
		pw.close();
	}

}
