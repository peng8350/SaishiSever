package com.peng.saishiserver.servlet.match;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.catalina.util.DateTool;
import org.apache.commons.httpclient.util.DateUtil;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.PushPayload;

import com.google.gson.JsonObject;
import com.jspsmart.upload.SmartUpload;
import com.peng.saishiserver.config.ServerConfig;
import com.peng.saishiserver.dao.DaoFactory;
import com.peng.saishiserver.dao.DaoProcessor;
import com.peng.saishiserver.entity.MatchInfo;
import com.peng.saishiserver.utils.JpushUtils;
import com.peng.saishiserver.utils.MyDateUtils;
import com.peng.saishiserver.utils.Responser;

/**
 * Servlet implementation class AddMatch
 */
@WebServlet("/AddMatch")
public class AddMatch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddMatch() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
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
		// TODO Auto-generated method stub
		response.setContentType("text/html,charset=UTF-8");
		SmartUpload smartUpload = new SmartUpload();
		PrintWriter pw = response.getWriter();
		try {
			smartUpload.initialize(this.getServletConfig(), request, response);
			smartUpload.upload();
			com.jspsmart.upload.File smartFile = smartUpload.getFiles()
					.getFile(0);
			if (!smartFile.isMissing()) {
				Responser responser = new Responser();
				String name = smartUpload.getRequest().getParameter("name");
				String score = smartUpload.getRequest().getParameter("score");
				String type1 = smartUpload.getRequest().getParameter("type1");
				String type2 = smartUpload.getRequest().getParameter("type2");
				String place = smartUpload.getRequest().getParameter("place");
				String urlString = smartUpload.getRequest().getParameter("url");
				String zhuban = smartUpload.getRequest().getParameter("zhuban");
				String titles = smartUpload.getRequest().getParameter("titles");
				String content = smartUpload.getRequest().getParameter(
						"content");
				String time = smartUpload.getRequest().getParameter("time");
				String fenlei = type1.equals("1") ? "IT开发"
						: type1.equals("2") ? "歌唱比赛"
								: type1.equals("3") ? "会计金融" : type1
										.equals("4") ? "游戏竞技" : type1
										.equals("5") ? "体育运动" : "艺术设计";
				DaoProcessor processor = DaoFactory.createProcessor();
				String sql = "insert into MatchInfo (name,score,type1,type2,time,place,zhuban,visitPerson,fenlei,webUrl,content,titles) values  (?,?,?,?,?,?,?,?,?,?,?,?)";
				boolean success = processor.Update(sql, new Object[] { name,
						Double.parseDouble(score), type1, type2, time, place,
						zhuban, 0, fenlei, urlString, content, titles });
				if (success) {
					MatchInfo match = (MatchInfo) processor.getBean(
							"select * from MatchInfo order by id desc limit 1",
							null, MatchInfo.class);
					// 向每个用户推送一个消息
					// 使用这个发送给所有客户端,以jsonobject {'response'},使用推送java
					// sdk实现这个业务逻辑
					responser.SendOk(pw);
					JSONObject obj = new JSONObject();
					obj.put("obj", match);
					obj.put("msgtype", 1);
					String saveFileName = getServletContext().getRealPath("/")
							+ ServerConfig.pic_pos + "Match" + "/" + match.getId();
					smartFile.saveAs(saveFileName);
					JpushUtils.PushAll(Message.content(obj.toString()));
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
