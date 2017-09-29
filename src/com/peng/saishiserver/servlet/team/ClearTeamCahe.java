package com.peng.saishiserver.servlet.team;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.jpush.api.report.MessagesResult.Message;

import com.google.gson.JsonObject;
import com.peng.saishiserver.utils.JpushUtils;

/**
 * Servlet implementation class ClearTeamCahe
 */
@WebServlet("/ClearTeamCahe")
public class ClearTeamCahe extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ClearTeamCahe() {
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
		String id = request.getParameter("id");
		JSONObject obj=new JSONObject();
		obj.put("msgtype", 2);
		obj.put("id", id);
		JpushUtils.PushAll(cn.jpush.api.push.model.Message.content(obj.toString()));
	}

}
