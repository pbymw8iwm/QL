package com.ql.wechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ai.appframe2.util.MD5;

/**
 * 工具类
 * @author hailu
 *
 */
public class WechatUtils {

	private static transient Log log = LogFactory.getLog(WechatUtils.class);
	/**
	 * 鉴权
	 * @param request
	 * @return
	 */
	public static boolean checkSignature(HttpServletRequest request){
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		
		if(signature == null || timestamp == null || nonce == null)
			return false;
				
		String[] tmpArr = { WechatCommons.TOKEN, timestamp, nonce };
		Arrays.sort(tmpArr);
		String tmpStr = ArrayToString(tmpArr);
		tmpStr = SHA1Encode(tmpStr);
		if (tmpStr.equalsIgnoreCase(signature)) {
			return true;
		} else {
			return false;
		} 
	}
	
	private static String ArrayToString(String [] arr){  
	    StringBuffer bf = new StringBuffer();  
	    for(int i = 0; i < arr.length; i++){  
	      bf.append(arr[i]);  
	    }  
	    return bf.toString();  
	}  
	
	private static String SHA1Encode(String sourceString) {  
	    String resultString = null;  
	    try {  
	       resultString = new String(sourceString);  
	       MessageDigest md = MessageDigest.getInstance("SHA-1");  
	       resultString = byte2hexString(md.digest(resultString.getBytes()));  
	    } catch (Exception ex) {  
	    }  
	    return resultString;  
	}  
	
	private static final String byte2hexString(byte[] bytes) {  
	    StringBuffer buf = new StringBuffer(bytes.length * 2);  
	    for (int i = 0; i < bytes.length; i++) {  
	        if (((int) bytes[i] & 0xff) < 0x10) {  
	            buf.append("0");  
	        }  
	        buf.append(Long.toString((int) bytes[i] & 0xff, 16));  
	    }  
	    return buf.toString().toUpperCase();  
	}
	
	public static String getInputStream(InputStream is)throws ServletException, IOException{
		InputStreamReader isr = null;
		BufferedReader br = null;
		try{
			StringBuffer sb = new StringBuffer();  
	        isr = new InputStreamReader(is, WechatCommons.Charset);  
	        br = new BufferedReader(isr);  
	        String s = null;  
	        while ((s = br.readLine()) != null) {  
	            sb.append(s);  
	        } 
	        String str = sb.toString();  
	        if(log.isDebugEnabled())
	        	log.debug("接收："+str);
	        return str;
		}
		finally{
			if(br != null)br.close();
			if(isr != null) isr.close();
			if(is != null) is.close();
		}
	}

	public static ReceiveXmlEntity getMsgEntity(String strXml){  
        ReceiveXmlEntity msg = null;  
        try {  
            if (strXml.length() <= 0 || strXml == null)  
                return null;  
                
            Document document = DocumentHelper.parseText(strXml);  
            
            Element root = document.getRootElement();  
            
            Iterator<?> iter = root.elementIterator();  
               
//            msg = new ReceiveXmlEntity();  
            
            Class<?> c = ReceiveXmlEntity.class;  
            msg = (ReceiveXmlEntity)c.newInstance();  
               
            while(iter.hasNext()){  
                Element ele = (Element)iter.next();  
                
                Field field = c.getDeclaredField(ele.getName());  
                
                Method method = c.getDeclaredMethod("set"+ele.getName(), field.getType());  
                  
                method.invoke(msg, ele.getText());  
            }  
        } catch (Exception e) {  
            log.error(e.getMessage(),e); 
        }  
        return msg;  
    }


	public static String formatXmlAnswer(String to, String from, String content) {  
        StringBuffer sb = new StringBuffer();  
        Date date = new Date();  
        sb.append("<xml><ToUserName><![CDATA[");  
        sb.append(to);  
        sb.append("]]></ToUserName><FromUserName><![CDATA[");  
        sb.append(from);  
        sb.append("]]></FromUserName><CreateTime>");  
        sb.append(date.getTime());  
        sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");  
        sb.append(content);  
        sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");  
        return sb.toString();  
    } 
	
	public static String formatUnifiedOrder(UnifiedOrderEntity order)throws Exception{
		Class<?> c = UnifiedOrderEntity.class;  
		Field[] fields = c.getDeclaredFields();
		
		Element root = DocumentHelper.createElement("xml");
		
        for(Field f : fields){
        	String name = f.getName();
        	Method method = c.getDeclaredMethod("get"+captureName(name));  
        	Object value = method.invoke(order);
        	if(value == null)
        		continue;
        	
			Element e = DocumentHelper.createElement(name);
			e.setText(value.toString());
			root.add(e);
		}
		return root.asXML();
	}
	
	public static UnifiedOrderResultEntity getUnifiedOrderResultEntity(String strXml){  
		UnifiedOrderResultEntity result = null;  
        try {  
            if (strXml.length() <= 0 || strXml == null)  
                return null;  
                
            Document document = DocumentHelper.parseText(strXml);  
            
            Element root = document.getRootElement();  
            
            Iterator<?> iter = root.elementIterator();  
               
            Class<?> c = UnifiedOrderResultEntity.class;  
            result = (UnifiedOrderResultEntity)c.newInstance();  
               
            while(iter.hasNext()){  
                Element ele = (Element)iter.next();  
                
                Field field = c.getDeclaredField(ele.getName());  
                
                Method method = c.getDeclaredMethod("set"+captureName(ele.getName()), field.getType());  
                  
                method.invoke(result, ele.getText());  
            }  
        } catch (Exception e) {  
            log.error(e.getMessage(),e); 
        }  
        return result;  
    }
	
	//首字母大写
	public static String captureName(String name) {
		char[] cs = name.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}

	/**
	 * 
	 * 发起https请求并获取结果
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return ReceiveJson
	 */
	public static ReceiveJson httpRequest(String requestUrl,String requestMethod, String outputStr)throws Exception {
		return ReceiveJson.getReceiveJson(httpRequestString(requestUrl,requestMethod,outputStr));
	}
	
	public static String httpRequestString(String requestUrl,String requestMethod, String outputStr)throws Exception {
		URL url = new URL(requestUrl);
		HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
				.openConnection();

		httpUrlConn.setDoOutput(true);
		httpUrlConn.setDoInput(true);
		httpUrlConn.setUseCaches(false);

		// 设置请求方式（GET/POST）
		httpUrlConn.setRequestMethod(requestMethod);
		if (WechatCommons.HttpGet.equalsIgnoreCase(requestMethod))
			httpUrlConn.connect();

		// 当有数据需要提交时
		if (null != outputStr) {
			OutputStream outputStream = httpUrlConn.getOutputStream();
			// 注意编码格式，防止中文乱码
			outputStream.write(outputStr.getBytes(WechatCommons.Charset));
			outputStream.close();
		}
		// 将返回的输入流转换成字符串
		String str = getInputStream(httpUrlConn.getInputStream());
		httpUrlConn.disconnect();
		return str;
	}

	public static String getRandomString(){
        return UUID.randomUUID().toString();
	}
	
	public static String getRandomString(int maxLen) {
		String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		int length = random.nextInt(maxLen);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	public static String[] getJsSignature(HttpServletRequest request){
		String timestamp = Long.toString(System.currentTimeMillis()/1000);
		String noncestr = getRandomString();
		StringBuffer url = request.getRequestURL();
		if(StringUtils.isNotBlank(request.getQueryString()))
			url.append("?").append(request.getQueryString());
		
		StringBuffer sb = new StringBuffer();
		sb.append("jsapi_ticket=").append(WechatCommons.JsTicket)
		  .append("&noncestr=").append(noncestr)
		  .append("&timestamp=").append(timestamp)
		  .append("&url=").append(url.toString());

		if(log.isDebugEnabled())
			log.debug(sb.toString());
		
		String s = SHA1Encode(sb.toString());
		return new String[]{timestamp,noncestr,s};
	}
	
	//微信消息字段定义太崩溃了，同一个字段，下划线啦大小写啦，别说不同的功能，同一个功能前后台都可能不一样，必须从文档里拷贝，稍不留意就错了
	public static String[] getPaySignature(String info,int fee,String ip,String url,String openId)throws Exception{
		String timestamp = Long.toString(System.currentTimeMillis()/1000);
		String noncestr = getRandomString(32);
		String prepayId = unifiedOrder(info, fee, ip, url, openId);
		
		StringBuffer sb = new StringBuffer();
		sb.append("appId=").append(WechatCommons.AppId)
		  .append("&nonceStr=").append(noncestr)
		  .append("&package=prepay_id=").append(prepayId)
		  .append("&signType=MD5")
		  .append("&timeStamp=").append(timestamp);

		if(log.isDebugEnabled())
			log.debug(sb.toString());
		
		String sign = getSign(sb.toString());
		
		if(log.isDebugEnabled())
			log.debug("PaySign:"+sign);
		
		return new String[]{timestamp,noncestr,prepayId,sign};
	}
	
	public static String unifiedOrder(String info,int fee,String ip,String url,String openId)throws Exception{
		UnifiedOrderEntity order = new UnifiedOrderEntity();
		order.setAppid(WechatCommons.AppId);
		order.setMch_id(WechatCommons.MchId);
		order.setDevice_info("WEB");
		order.setNonce_str(getRandomString(32));
		order.setBody(info);
		order.setOut_trade_no(System.currentTimeMillis()+getRandomString(5));
		order.setTotal_fee(String.valueOf(fee));
		order.setSpbill_create_ip(ip);
		order.setNotify_url(url);
		order.setTrade_type("JSAPI");
		order.setOpenid(openId);
		
		StringBuffer sb = new StringBuffer();
		sb.append("appid=").append(order.getAppid())
		  .append("&body=").append(order.getBody())
		  .append("&device_info=").append(order.getDevice_info())
		  .append("&mch_id=").append(order.getMch_id())
		  .append("&nonce_str=").append(order.getNonce_str())
		  .append("&notify_url=").append(order.getNotify_url())
		  .append("&openid=").append(order.getOpenid())
		  .append("&out_trade_no=").append(order.getOut_trade_no())
		  .append("&spbill_create_ip=").append(order.getSpbill_create_ip())
		  .append("&total_fee=").append(order.getTotal_fee())
		  .append("&trade_type=").append(order.getTrade_type());
		
		order.setSign(getSign(sb.toString()));
		
		if(log.isDebugEnabled())
			log.debug("UnifiedOrderSign:"+order.getSign());
		
		String str = httpRequestString(WechatCommons.Url_UnifiedOrder,WechatCommons.HttpPost,formatUnifiedOrder(order));

		if(log.isDebugEnabled())
			log.debug("UnifiedOrderResult:"+str);
		
		UnifiedOrderResultEntity result = getUnifiedOrderResultEntity(str);
		if("SUCCESS".equals(result.getReturn_code())){
			if("SUCCESS".equals(result.getResult_code())){
				return result.getPrepay_id();
			}
			else{
				throw new Exception(result.getErr_code()+":"+result.getErr_code_des());
			}
		}
		else{
			throw new Exception(result.getReturn_code()+":"+result.getReturn_msg());
		}
	}
	
	public static String getSign(String str){
		if(log.isDebugEnabled())
			log.debug(str+"&key="+WechatCommons.APIKey);
		
		MD5 m = new MD5();
		return m.getMD5ofStr(str+"&key="+WechatCommons.APIKey).toUpperCase();
	}
}
