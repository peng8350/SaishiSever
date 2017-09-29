package com.peng.saishiserver.servlet.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

/**
 * Servlet implementation class getShoucang
 */
@WebServlet("/getShoucang")
public class getShoucang extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getShoucang() {
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
		PrintWriter pw=null;
		if (GzipUtilities.isGzipSupported(request)
				&& !GzipUtilities.isGzipDisabled(request)) {
			pw = GzipUtilities.getGzipWriter(response);
			response.setHeader("Content-Encoding", "gzip");
		} else {
			pw = response.getWriter();
		}
		String id =request.getParameter("id");
		DaoProcessor processor = DaoFactory.createProcessor();
		List<MatchInfo> list=null;
		List<Integer> ids=(List<Integer>) processor.getValues("select match_id from User_Shoucang where id = ?", new Object[]{id});
		if (ids.size()==0) {
			list= new ArrayList<>();
		}
		else{
			String temp = "";
			for(int i=0;i<ids.size();i++){
				if (i==ids.size()-1) {
					temp+=ids.get(i);
				}
				else
				temp+=ids.get(i)+",";
			}
			String sql = "select * from MatchInfo where id in("+temp+")";
		}
		pw.println(JSONArray.fromObject(list));
		
		pw.close();
	}

}
