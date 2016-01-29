package com.ql.wechat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MsgForwardTool {

	private static transient Log log = LogFactory.getLog(MsgForwardTool.class);
	
	public static boolean sendTextMsg(String toUser,String content)throws Exception{
		String info = "{"
				+"\"touser\":\""+toUser+"\","
				+"\"msgtype\":\"text\","
				+"\"text\":{"
				+"\"content\":\""+content+"\""
				+"}"
				+"}";
		ReceiveJson result = WechatUtils.httpRequestJson(WechatCommons.getUrlSendMsg(), WechatCommons.HttpPost, info);
		boolean isError = result.isError();
		if(isError)
			log.error(result.getErrMsg());
		return isError;
	}
	
	public static boolean sendImage(String toUser,String mediaId)throws Exception{
		String info = "{"
				+"\"touser\":\""+toUser+"\","
				+"\"msgtype\":\"image\","
				+"\"image\":{"
				+"\"media_id\":\""+mediaId+"\""
				+"}"
				+"}";
		ReceiveJson result = WechatUtils.httpRequestJson(WechatCommons.getUrlSendMsg(), WechatCommons.HttpPost, info);
		boolean isError = result.isError();
		if(isError)
			log.error(result.getErrMsg());
		return isError;
	}
	
	public static boolean sendVoice(String toUser,String mediaId)throws Exception{
		String info = "{"
				+"\"touser\":\""+toUser+"\","
				+"\"msgtype\":\"voice\","
				+"\"voice\":{"
				+"\"media_id\":\""+mediaId+"\""
				+"}"
				+"}";
		ReceiveJson result = WechatUtils.httpRequestJson(WechatCommons.getUrlSendMsg(), WechatCommons.HttpPost, info);
		boolean isError = result.isError();
		if(isError)
			log.error(result.getErrMsg());
		return isError;
	}
	
	public static boolean sendVideo(String toUser,String mediaId,String thumbMediaId)throws Exception{
		String info = "{"
				+"\"touser\":\""+toUser+"\","
				+"\"msgtype\":\"video\","
				+"\"video\":{"
				+"\"media_id\":\""+mediaId+"\","
			    +"\"thumb_media_id\":\""+thumbMediaId+"\","
				+"\"title\":\"\","
				+"\"description\":\"\""
				+"}"
				+"}";
		ReceiveJson result = WechatUtils.httpRequestJson(WechatCommons.getUrlSendMsg(), WechatCommons.HttpPost, info);
		boolean isError = result.isError();
		if(isError)
			log.error(result.getErrMsg());
		return isError;
	}
}
