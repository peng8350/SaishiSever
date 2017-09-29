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
import com.peng.saishiserver.entity.TeamInfo;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class getUserTeam
 */
@WebServlet("/getUserTeam")
public class getUserTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getUserTeam() {
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
		List<TeamInfo> list1=null;
		List<TeamInfo> list2=null;
		List<Integer> ids1=(List<Integer>) processor.getValues("select team_id from User_team where id = ? and type =1", new Object[]{id});
		List<Integer> ids2=(List<Integer>) processor.getValues("select team_id from User_team where id = ? and type =2", new Object[]{id});
		if (ids1.size()==0) {
			list1= new ArrayList<>();
		}
		else{
			String temp = "";
			for(int i=0;i<ids1.size();i++){
				if (i==ids1.size()-1) {
					temp+=ids1.get(i);
				}
				else
				temp+=ids1.get(i)+",";
			}
			String sql = "select * from TeamInfo where id in("+temp+")";
			list1=processor.getListBean(sql, null, TeamInfo.class);
		}
		if (ids2.size()==0) {
			list2= new ArrayList<>();
		}
		else{
			String temp = "";
			for(int i=0;i<ids2.size();i++){
				if (i==ids2.size()-1) {
					temp+=ids2.get(i);
				}
				else
				temp+=ids2.get(i)+",";
			}
			String sql = "select * from TeamInfo where id in("+temp+")";
			list2=processor.getListBean(sql, null, TeamInfo.class);
		}
		JSONObject obj  = new JSONObject();
		obj.put("list1", list1);
		obj.put("list2", list2);
		pw.println(obj);
		
		pw.close();
	}

}
