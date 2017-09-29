package com.peng.saishiserver.servlet.search;

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
import com.peng.saishiserver.entity.SearchInfo;
import com.peng.saishiserver.entity.User;
import com.peng.saishiserver.utils.GzipUtilities;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class getAllSearch
 * 获取所有的搜索数据,排序按照次数的大小来排序,限制用户的条数为30条　
 */
@WebServlet("/getAllSearch")
public class getAllSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getAllSearch() {
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
		String sql = "select * from Search order by time desc limit 0,30";
		DaoProcessor processor = DaoFactory.createProcessor();
		List<SearchInfo> list = processor.getListBean(sql, null, SearchInfo.class);
		List<String> send_obj = new ArrayList<>();
		for(SearchInfo info:list){
			send_obj.add(info.getContent());
		}
		pw.println(send_obj);
		
		pw.close();
	}

}
