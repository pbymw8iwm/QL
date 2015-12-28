package com.ql.wechat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 定期刷新AccessToken
 * @author hailu
 *
 */
public class TokenThread implements Runnable {  
	private static transient Log log = LogFactory.getLog(TokenThread.class);
      
    public void run() {  
        while (true) {  
            try {  
            	//获取token
            	ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.Url_Token,WechatCommons.HttpGet,null);
            	if(json.isError()){
            		log.error(json.getErrCode()+":"+json.getErrMsg());
            		Thread.sleep(60 * 1000);
            		continue;
	            }
            	String token = json.getAccessToken();
            	
                if (StringUtils.isNotBlank(token)) {  
                	WechatCommons.AccessToken = token; 
                	
                	//获取jstoken
                	ReceiveJson json2 = WechatUtils.httpRequestJson(WechatCommons.getUrlJsTicket(),WechatCommons.HttpGet,null);
                	if(json2.isError()){
                		log.error(json2.getErrCode()+":"+json2.getErrMsg());
                		Thread.sleep(60 * 1000);
                		continue;
    	            }
                	String jsticket = json2.getTicket();
                	
                    if (StringUtils.isNotBlank(jsticket)) {  
                    	WechatCommons.JsTicket = jsticket; 
                    } else {  
                        // 如果js_token为null，60秒后再获取  
                        Thread.sleep(60 * 1000);  
                        continue;
                    }
                    
                    // 休眠
                    Thread.sleep((json.getExpiresIn() - 200) * 1000);  
                } else {  
                    // 如果access_token为null，60秒后再获取  
                    Thread.sleep(60 * 1000);  
                }  
            } catch (InterruptedException e) {  
                log.error(e.getMessage(), e); 
                try {  
                    Thread.sleep(60 * 1000);  
                } catch (InterruptedException e1) {  
                    
                }   
            }  
            catch(Exception ex){
            	log.error(ex.getMessage(), ex);  
            }
        }  
    }  

}
