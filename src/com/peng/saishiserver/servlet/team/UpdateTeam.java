package com.peng.saishiserver.servlet.team;

import java.io.IOException;
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
 * Servlet implementation class UpdateTeam
 */
@WebServlet("/UpdateTeam")
public class UpdateTeam extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateTeam() {
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
        response.setContentType("text/html,charset=UTF-8");  
        SmartUpload smartUpload = new SmartUpload();  
  
        try {  
            smartUpload.initialize(this.getServletConfig(), request, response);  
            smartUpload.upload();  
            com.jspsmart.upload.File smartFile = smartUpload.getFiles().getFile(0);  
            if (!smartFile.isMissing()) {  
            			
        		String user_id = smartUpload.getRequest().getParameter("id");
        		String name = smartUpload.getRequest().getParameter("name");
        		int neeedperson = Integer.parseInt(smartUpload.getRequest().getParameter("need"));
        		String intro = smartUpload.getRequest().getParameter("intro");
        		String yaoqiu = smartUpload.getRequest().getParameter("yaoqiu");
        		String plan = smartUpload.getRequest().getParameter("plan");
        		String target = smartUpload.getRequest().getParameter("target");
        		String sql = "update TeamInfo set intro = ?,name = ?,yaoqiu =?,plan = ?,target = ?,needperson = ? where id = ?";
        		DaoProcessor processor = DaoFactory.createProcessor(); 
        		Responser responser = new Responser();
        		if(processor.Update(sql, new Object[]{intro,name,yaoqiu,plan,target,neeedperson,user_id})){
                    String saveFileName =getServletContext().getRealPath("/")+ ServerConfig.pic_pos + "Team" + "/"
    						+ user_id;
        		     smartFile.saveAs(saveFileName, smartUpload.SAVE_PHYSICAL);  
        			responser.SendOk(response.getWriter());
        		}
        		else{
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
