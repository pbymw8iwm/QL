package com.ql.common;

import java.io.PrintWriter;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.ai.appframe2.web.CustomProperty;

public class HttpJsonUtil {

	public static final String JSON_CONTENT_TYPE = "application/json; charset=UTF-8";
	
	public static void showError(ServletResponse response, String message) throws Exception {
		show(response,message,false);
	}

	public static void showInfo(HttpServletResponse response, String message) throws Exception {
		show(response,message,true);
	}
	
	private static void show(ServletResponse response, String message,boolean isInfo)throws Exception{
		JSONObject json = new JSONObject();  
		json.put("flag", isInfo);
		json.put("msg", message);
		response.setContentType(JSON_CONTENT_TYPE);
		PrintWriter writer = response.getWriter();
		writer.print(json.toString());
	}
	
	public static void showInfo(HttpServletResponse response, CustomProperty cp) throws Exception {
		JSONObject json = new JSONObject();
		Object[] keys = cp.getKeyArray();
		for(int i=0;i<keys.length;i++){
			String key = keys[i].toString();
			json.put(key, cp.get(key));
		}		
		json.put("flag", true);
		response.setContentType(JSON_CONTENT_TYPE);
		PrintWriter writer = response.getWriter();
		writer.print(json.toString());
	}
}
