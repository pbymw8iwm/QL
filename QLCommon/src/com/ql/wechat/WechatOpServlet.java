package com.ql.wechat;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.privilege.LoginException;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.privilege.UserManagerFactory;
import com.ai.appframe2.web.BaseServer;
import com.ai.appframe2.web.HttpUtil;
import com.ql.bo.WechatUserBean;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.ivalues.IWechatUserValue;

/**
 * 微信菜单连接
 * @author linhl
 *
 */
public class WechatOpServlet extends HttpServlet {

	private static transient Log log = LogFactory.getLog(WechatOpServlet.class);
	
	/**
	 * Constructor of the object.
	 */
	public WechatOpServlet() {
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

		request.setCharacterEncoding(WechatCommons.Charset);  
        response.setCharacterEncoding(WechatCommons.Charset);  

        //微信网页授权传过来的参数
        String code = request.getParameter("code");
        //业务逻辑带的参数
        String type = request.getParameter("state");
        if(type == null){
        	 OutputStream os = response.getOutputStream();  
             os.write("未知访问".getBytes(WechatCommons.Charset));  
             os.flush();  
             os.close(); 
             return;
        }
                
        IWechatUserValue wechatUser = null;
        UserInfoInterface user = SessionManager.getUser();
        try{
        	if(StringUtils.isBlank(code) == false){
        		//获取网页授权以取得open_id
	            ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.getUrlOauth2(code),WechatCommons.HttpGet,null);
	            if(json.isError()){
	            	log.error(json.getErrMsg());
	        		if(user != null){
	        			wechatUser = new WechatUserBean();
	        			wechatUser.setUserid(user.getID());
	        			wechatUser.setUsertype(DataType.getAsLong(user.get("type")));
	        		}
//	            	throw new Exception(json.getErrMsg());
	            }
	            else{
		            String openId = json.getOpenId();
		        	wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, openId);
		        	//与session中的用户不一致
		        	if(wechatUser != null && user != null){
		        		if(wechatUser.getUserid() != user.getID())
		        			user = null;
		        	}
	            }
        	}
        	else if(user != null){
        		long userId = HttpUtil.getAsLong(request, "user");
        		long userType = HttpUtil.getAsLong(request, "type");
        		if(userId == user.getID()){
        			wechatUser = new WechatUserBean();
        			wechatUser.setUserid(userId);
        			wechatUser.setUsertype(userType);
        		}
        		else
        			user = null;
        	}
        }
        catch(Exception ex){
        	log.error(ex.getMessage(),ex);
        }
        
        if(wechatUser == null){
        	response.setContentType("text/html");
        	OutputStream os = response.getOutputStream();  
            os.write("未知用户".getBytes(WechatCommons.Charset));  
            os.flush();  
            os.close(); 
            return;
        }
        
        //登录
        try{
        	if(user == null)
        		user = login(request,wechatUser);
        }
        catch(Exception ex){
        	log.error(ex.getMessage(),ex);
        	response.setContentType("text/html");
        	OutputStream os = response.getOutputStream();  
            os.write(("登录异常:"+ex.getMessage()).getBytes(WechatCommons.Charset));  
            os.flush();  
            os.close(); 
            return;
        }
        
        String url = WechatCommons.WechatOp.getOpUrl(request, response, type);
        //必须redirect，否则微信的js校验不通过
        if(url != null)
        	response.sendRedirect(url);
//        	request.getRequestDispatcher(url).forward(request, response);
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

		doGet(request, response); 
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

	private UserInfoInterface login(HttpServletRequest request,IWechatUserValue wechatUser) throws Exception{
		request.setAttribute("LoginType",wechatUser.getUsertype());
		request.setAttribute("LoginId",wechatUser.getUserid());
		
		UserInfoInterface user = UserManagerFactory.getUserManager().loginIn(null, null, 0,0, request);
		if (user == null) {
			throw new LoginException(LoginException.USER_LOGIN_USERNOTFOUND);
		}
		UserManagerFactory.getUserManager().setLogined(user);
		user.setSessionID(request.getSession().getId());
		//在session中放置一个标志序列号
		request.getSession().setAttribute(BaseServer.WBS_USER_ATTR, user.getSerialID());
		//将当前用户的信息发送到后台
		SessionManager.setUser(user);
		return user;
	}
}
