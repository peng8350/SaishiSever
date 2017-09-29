package com.peng.saishiserver.servlet.news;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import cn.jpush.api.push.model.Message;

import com.jspsmart.upload.SmartUpload;
import com.peng.saishiserver.config.ServerConfig;
import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.MatchInfo;
import com.peng.saishiserver.entity.NewInfo;
import com.peng.saishiserver.utils.JpushUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class AddNew
 */
@WebServlet("/AddNew")
public class AddNew extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNew() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		response.setContentType("text/html,charset=UTF-8");
		SmartUpload smartUpload = new SmartUpload();
		PrintWriter pw = response.getWriter();
		DaoProcessor processor = DaoFactory.createProcessor();

		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			com.jspsmart.upload.File smartFile = smartUpload.getFiles()
					.getFile(0);
			if (!smartFile.isMissing()) {
				Responser responser = new Responser();
				String title = smartUpload.getRequest().getParameter("title");
				String content = smartUpload.getRequest().getParameter(
						"content");
				String webUrl = smartUpload.getRequest().getParameter("webUrl");
				String time = smartUpload.getRequest().getParameter("time");
				String id = smartUpload.getRequest().getParameter("id");
				String sql = "insert into News (matchid,title,time,content,webUrl) values (?,?,?,?,?)";

				boolean success = processor.Update(sql, new Object[] { id,
						title, time, content, webUrl });
				// 判断是否成功
				if (success) {
					// 保存文件的操作
					String sql1 = "select  max(id) from News";
					int max_id = (int) processor.getValue(sql1, null);
					String saveFileName = getServletContext().getRealPath("/")
							+ ServerConfig.pic_pos + "News" + "/" + max_id;
					smartFile.saveAs(saveFileName);

					responser.SendOk(pw);
				} else {
					responser.SendFailed(pw);
				}

			} else {

				response.getWriter().print("missing...");
			}
		} catch (Exception e) {
			response.getWriter().print(e);
		}

		
		pw.close();
	}

}
