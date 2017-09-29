package com.peng.saishiserver.utils;

import java.io.PrintWriter;

import com.peng.saishiserver.service.ResponseService;

import net.sf.json.JSONObject;


/**
 * 相应者,用来回返客户端的一些数据
 * @author peng
 *	1.把jsonobject以{"result":"ok"}的形式返回给客户端
 */
public class Responser implements ResponseService {

	/**
	 * 
	 * 返回ok给客户端
	 * 以{"result":"ok"}
	 */
	@Override
	public void SendOk(PrintWriter writer) {
		// TODO Auto-generated method stub
		JSONObject res=new JSONObject();
		res.put("result", "ok");
		writer.println(res);
	}

	/**
	 * 返回failed给客户端
	 * 以{"result":"failed"}
	 */
	@Override
	public void SendFailed(PrintWriter writer) {
		// TODO Auto-generated method stub
		JSONObject res=new JSONObject();
		res.put("result", "failed");
		writer.println(res);
	}

	@Override
	public void AddOk(JSONObject param) {
		// TODO Auto-generated method stub
		param.put("result", "ok");
		
	}
	
	

}
