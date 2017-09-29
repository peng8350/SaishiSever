package com.peng.saishiserver.servlet.upload;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.peng.saishiserver.config.ServerConfig;

/**
 * Servlet implementation class RadioUpload
 */
@WebServlet("/RadioUpload")
public class RadioUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RadioUpload() {
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");
		SmartUpload smartUpload = new SmartUpload();
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();

			com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);
			if (!smartFile.isMissing()) {
				// getServletContext().getRealPath("/")
				String id = smartUpload.getRequest().getParameter("id");
				String saveFileName = getServletContext().getRealPath("/") + ServerConfig.radio_pos + "/" + id;
				smartFile.saveAs(saveFileName, SmartUpload.SAVE_PHYSICAL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
