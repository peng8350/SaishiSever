package com.peng.saishiserver.service;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

public interface ResponseService {
	
	//返回给客户端{result :ok}
	public void SendOk(PrintWriter writer);
	
	//返回给客户端{result:failed}
	public void SendFailed(PrintWriter writer);
	
	/**
	 * 给jsonobject拼接result:ok
	 */
	public void AddOk(JSONObject param);

}
