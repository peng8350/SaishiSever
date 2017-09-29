package com.peng.saishiserver.servlet.user;

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

import net.sf.json.JSONObject;

/**
 * Servlet implementation class UserExist
 */
@WebServlet("/UserExist")
public class UserExist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserExist() {
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
		String user = request.getParameter("user");
		String sql= "select * from User where user = ?";
		DaoProcessor processor = DaoFactory.createProcessor();
		int count = processor.getCount(sql, new Object[]{user});
		
		Responser responser = new Responser();
		JSONObject obj=new JSONObject();
		obj.put("have", count==0?"false":"true");
		pw.print(obj);
		
		pw.close();
	}

}
