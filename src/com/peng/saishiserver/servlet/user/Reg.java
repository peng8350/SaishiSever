package com.peng.saishiserver.servlet.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.jspsmart.upload.SmartUpload;
import com.peng.saishiserver.config.ServerConfig;
import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class Reg 注册servlet 传入用户的账号,密码和手机号码,邮箱
 * 以result:ok或者result:failed
 * 
 * @author peng
 */
@WebServlet("/Reg")
public class Reg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Reg() {
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

		// TODO Auto-generated method stub
		Responser responser = new Responser();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");
		SmartUpload smartUpload = new SmartUpload();
		DaoProcessor processor = DaoFactory.createProcessor();
		System.out.println(processor);
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			com.jspsmart.upload.File smartFile = smartUpload.getFiles()
					.getFile(0);
			if (!smartFile.isMissing()) {
				// getServletContext().getRealPath("/")
				String user = smartUpload.getRequest().getParameter("user");
				String pswd = smartUpload.getRequest().getParameter("pswd");
						
				String name = smartUpload.getRequest().getParameter("name");
				String intro = smartUpload.getRequest().getParameter("intro");
				String sex = smartUpload.getRequest().getParameter("sex");
				String sql = "insert into User (user,pswd,intro,username,sex,phone) values(?,?,?,?,?,?)";
				if (processor.Update(sql, new Object[] {user,pswd ,intro,name, sex, user })) {
					responser.SendOk(response.getWriter());
				} else {
					responser.SendFailed(response.getWriter());
				}
				int insert_id = (int) processor.getValue("select max(id) from User", null);
				String saveFileName = getServletContext().getRealPath("/")
						+ ServerConfig.pic_pos + "User" + "/" + insert_id;
				smartFile.saveAs(saveFileName, SmartUpload.SAVE_PHYSICAL);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
		response.getWriter().close();
		
	}


}
