package com.peng.saishiserver.servlet.pingjia;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * 添加一条评价 传入形参有: target(比赛的id),name,content,time,icon(用户头像)
 */
@WebServlet("/Addpingjia")
public class Addpingjia extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Addpingjia() {
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
		// 获得对应的参数
		String name = request.getParameter("name");
		String content = request.getParameter("content");
		String time = MyDateUtils.getNowTime();
		String head = request.getParameter("icon");
		// 比赛条目的id
		String match_id = request.getParameter("id");

		DaoProcessor processor = DaoFactory.createProcessor();
		String sql1 = "insert into pingjia (name,content,picUrl,time,good,bad) values(?,?,?,?,?,?)";
		String sql2 = "insert into Match_Pingjia values(?,?)";
		// 添加一条评价
		boolean addPingjia = processor.Update(sql1, new Object[] { name, content, head, time,"",""});
		// 从中修改matter表中对应他的pingjias,即是评价条目的id
		int insert_id = (int) processor.getValue("select max(id) from pingjia", null);
		boolean addMatch_Pingjia = processor.Update(sql2, new Object[] { match_id, insert_id });
		boolean success = addMatch_Pingjia & addPingjia;
		Responser responser = new Responser();
		if (success) {
			responser.SendOk(pw);
		} else {
			responser.SendFailed(pw);
		}
		
		pw.close();
	}

}
