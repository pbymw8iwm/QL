package com.ql.wechat;

import net.sf.json.JSONObject;

/**
 * 处理返回的json消息
 * @author hailu
 *
 */
public class ReceiveJson {
	
	public static ReceiveJson getReceiveJson(String json){
		return new ReceiveJson(JSONObject.fromObject(json));
	}

	private static final String S_Errcode = "errcode";
	private static final String S_Errmsg = "errmsg";
	
	private static final String S_OpenId = "openid";
	private static final String S_Accesstoken = "access_token";
	private static final String S_Expiresin = "expires_in";
	
	private static final String S_Ticket = "ticket";
	private static final String S_ExpireSeconds = "expire_seconds";
	private static final String S_Url = "url";
	
	private static final String S_Nickname = "nickname";
	private static final String S_Sex = "sex";
	private static final String S_City = "city";
	private static final String S_Province = "province";
	private static final String S_Headimgurl = "headimgurl";
	

	private JSONObject jsonObj = null;
	
	private ReceiveJson(JSONObject jsonObj){
		this.jsonObj = jsonObj;
	}
	
	public boolean isError(){
		try{
			int code = getErrCode();
			return code > 0;
		}
		catch(Exception ex){
			return false;
		}
	}
	
	public String getOpenId(){
		return jsonObj.getString(S_OpenId);
	}
	
	public String getAccessToken(){
		return jsonObj.getString(S_Accesstoken);
	}

	public long getExpiresIn(){
		return jsonObj.getLong(S_Expiresin);
	}

	public String getErrMsg(){
		return jsonObj.getString(S_Errmsg);
	}
	
	public int getErrCode(){
		return jsonObj.getInt(S_Errcode);
	}

	public String getTicket(){
		return jsonObj.getString(S_Ticket);
	}
	
	public String getUrl(){
		return jsonObj.getString(S_Url);
	}

	public long getExpireSeconds(){
		return jsonObj.getLong(S_ExpireSeconds);
	}
	
	public String getNickname(){
		return jsonObj.getString(S_Nickname);
	}
	public String getCity(){
		return jsonObj.getString(S_City);
	}
	public String getProvince(){
		return jsonObj.getString(S_Province);
	}
	public String getHeadimgurl(){
		return jsonObj.getString(S_Headimgurl);
	}
	public long getSex(){
		return jsonObj.getLong(S_Sex);
	}
}
