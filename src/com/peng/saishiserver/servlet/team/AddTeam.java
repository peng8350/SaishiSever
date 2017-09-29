package com.peng.saishiserver.servlet.team;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.peng.saishiserver.config.ServerConfig;
import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class AddTeam
 */
@WebServlet("/AddTeam")
public class AddTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddTeam() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html,charset=utf-8");
		SmartUpload smartUpload = new SmartUpload();
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			com.jspsmart.upload.File smartFile = smartUpload.getFiles()
					.getFile(0);
			com.jspsmart.upload.File smartFile2 = smartUpload.getFiles()
					.getFile(1);

			if (!smartFile.isMissing()) {

				String group_id = smartUpload.getRequest().getParameter(
						"groupid");
				String user_id = smartUpload.getRequest().getParameter("id");
				String name = smartUpload.getRequest().getParameter("name");
				String matchname = smartUpload.getRequest().getParameter(
						"matchname");
				int neeedperson = Integer.parseInt(smartUpload.getRequest()
						.getParameter("need"));
				String intro = smartUpload.getRequest().getParameter("intro");
				String yaoqiu = smartUpload.getRequest().getParameter("yaoqiu");
				String plan = smartUpload.getRequest().getParameter("plan");
				String target = smartUpload.getRequest().getParameter("target");
				String type = smartUpload.getRequest().getParameter("type");
				String tags = smartUpload.getRequest().getParameter("tags");
				String owner = smartUpload.getRequest().getParameter("owner");
				String time = smartUpload.getRequest().getParameter("time");
				String match_id = smartUpload.getRequest().getParameter(
						"matchid");
				String sql = "insert into TeamInfo (groupid,matchid,nowperson,name,matchName,needperson,time,intro,yaoqiu,plan,target,type,tags,owner) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				DaoProcessor processor = DaoFactory.createProcessor();
				Responser responser = new Responser();
			
				if (processor.Update(sql, new Object[] { group_id, match_id, 1,
						name, matchname, neeedperson, time, intro, yaoqiu,
						plan, target, type, tags,owner})) {
					int insert_id = (int) processor.getValue(
							"select max(id) from TeamInfo", null);
					String saveFileName = getServletContext().getRealPath("/")
							+ ServerConfig.pic_pos + "Team" + "/" + insert_id;
					System.out.println(saveFileName);
					processor.Update("insert into User_team values(?,?,?)",
							new Object[] { user_id, insert_id, 1 });
					smartFile2.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);
					smartFile.saveAs(getServletContext().getRealPath("/")
							+ ServerConfig.pic_pos + "qrcode" + "/" + group_id,
							smartUpload.SAVE_PHYSICAL);
					responser.SendOk(response.getWriter());
				} else {
					responser.SendFailed(response.getWriter());
				}

			} else {

				response.getWriter().print("missing...");
			}
		} catch (Exception e) {
			response.getWriter().print(e);
		}

	}

}
