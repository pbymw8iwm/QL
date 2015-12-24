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

/**
 * 响应微信推送的消息
 * @author hailu
 *
 */
public class WechatServlet extends HttpServlet {

	private static transient Log log = LogFactory.getLog(WechatServlet.class);
	/**
	 * Constructor of the object.
	 */
	public WechatServlet() {
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
		
        boolean isSignature = WechatUtils.checkSignature(request);
        String result = null;  

    	if (isSignature){
	        /** 判断是否是微信接入激活验证，只有首次接入验证时才会收到echostr参数，此时需要把它直接返回 */ 
	        String echostr = request.getParameter(WechatCommons.KeyEchostr);  
	        if (echostr != null && echostr.length() > 1) {  
	        	result = echostr;
	        } else {  
	        	String xml = WechatUtils.getInputStream(request.getInputStream());
	        	ReceiveXmlEntity xmlEntity = WechatUtils.getMsgEntity(xml);
	        	if(xmlEntity == null || xmlEntity.getMsgType() == null)
	        		result = "";
	        	else{
	        		//处理事件
	        		if(xmlEntity.getMsgType().equals(WechatCommons.KeyEvent)){
	        			if(xmlEntity.getEvent().equals(WechatCommons.KeyEventSubscribe)){
	        				//订阅
	        				result = WechatCommons.WechatOp.processSubscribe(xmlEntity); 
	        			}
	        			else if(xmlEntity.getEvent().equals(WechatCommons.KeyEventUnsubscribe)){
	        				//取消订阅
	        				result = WechatCommons.WechatOp.processUnsubscribe(xmlEntity); 
	        			}
	        			else if(xmlEntity.getEvent().equals(WechatCommons.KeyEventScan)){
	        				//二维码扫描
	        				result = WechatCommons.WechatOp.processScan(xmlEntity); 
	        			}
	        		}
	        		//处理普通消息
	        		else if(xmlEntity.getMsgType().equals(WechatCommons.KeyText)
	    	        		|| xmlEntity.getMsgType().equals(WechatCommons.KeyImage)
	    	        		|| xmlEntity.getMsgType().equals(WechatCommons.KeyVoice)
	    	        		|| xmlEntity.getMsgType().equals(WechatCommons.KeyVideo)){
        				result = WechatCommons.WechatOp.processMsg(xmlEntity); 
	        		}
		            if(StringUtils.isNotBlank(result))
		                result = WechatUtils.formatXmlAnswer(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), result);
	        	}
	        }  
    	}
    	else{
    		response.setContentType("text/html");
    		result = "仅可通过微信访问";
    	}
        
        if(log.isDebugEnabled())
        	log.debug("回复："+result);
   
        if(result != null){
	        try {  
	            OutputStream os = response.getOutputStream();  
	            os.write(result.getBytes(WechatCommons.Charset));  
	            os.flush();  
	            os.close();  
	        } catch (Exception ex) {  
	        	log.error(ex.getMessage(),ex);
	        }  
        }

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

}
