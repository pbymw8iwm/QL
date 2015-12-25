package com.ql.wechat;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;
import com.ai.appframe2.web.HttpUtil;
import com.ql.cache.CfStaticDataCacheImpl;
import com.ql.cache.WechatUserCacheImpl;

/**
 * 应用启动时获取AccessToken
 * 临时提供：t=t 获取当前的AccessToken,t=c 刷新用户缓存,t=s 刷新配置数据
 * @author hailu
 *
 */
public class WechatInitServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WechatInitServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("t");
		String result = null;
		if("t".equals(type)){
			result = WechatCommons.AccessToken;
		}
		else{
			ICache cache = null;
			if("c".equals(type))
				cache = (ICache)CacheFactory._getCacheInstances().get(WechatUserCacheImpl.class);
			else if("s".equals(type))
				cache = (ICache)CacheFactory._getCacheInstances().get(CfStaticDataCacheImpl.class);
			if(cache != null){
				try {
					cache.refresh();
					result = "finished";
					
				} catch (Exception e) {
					e.printStackTrace();
					result = e.getMessage();
				}
			}
			else
				result = "未知操作";
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(result);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.print("    This is ");
		out.print(this.getClass());
		out.println(", using the POST method");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		HttpUtil.setEncoding(HttpUtil.CHARSET_UTF8);
		new Thread(new TokenThread()).start();
		
		//触发一下缓存
		ICache cache = (ICache)CacheFactory._getCacheInstances().get(WechatUserCacheImpl.class);
	}

}
