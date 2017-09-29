package com.peng.saishiserver.servlet.match;

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
import com.peng.saishiserver.entity.Pingjia;
import com.peng.saishiserver.service.CaheListener;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getAllPingjias
 */
@WebServlet("/getAllPingjias")
public class getAllPingjias extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public getAllPingjias() {
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
		//传入比赛bean的id
		int id =Integer.parseInt(request.getParameter("id"));
		System.out.println("比赛id"+id);
		final DaoProcessor processor = DaoFactory.createProcessor();
		List<Integer> pingjia_ids=(List<Integer>) processor.getValues("select pingjia_id from Match_Pingjia where id = ?", new Object[]{id});
		List<Pingjia> list=null;
		if (pingjia_ids.size()==0) {
			list = new ArrayList<>();
		}
		else{
			String temp = "";
			for(int i=0;i<pingjia_ids.size();i++){
				if (i==pingjia_ids.size()-1) {
					temp+=pingjia_ids.get(i);
				}
				else
				temp+=pingjia_ids.get(i)+",";
			}
			final String sql = "select * from pingjia where id in("+temp+") order by id desc";
			list= processor.getListBean(sql, null, Pingjia.class);
		}
		System.out.println(list.size());
		pw.println(JSONArray.fromObject(list));
		
		pw.close();
	}

}
