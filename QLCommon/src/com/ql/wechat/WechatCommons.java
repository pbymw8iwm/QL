package com.ql.wechat;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * 微信常量定义
 * @author hailu
 *
 */
public class WechatCommons {
	public static final String Charset = "UTF-8";
	public static final String HttpPost = "POST";
	public static final String HttpGet = "GET";

	public static final String TOKEN = "weixin";
	public static String AppId = null;
	public static String AppSecret = null;
	public static String ServerIp = null;
	public static String MchId = null; // 康复在线  商户号
	public static String APIKey = null; //API密钥
	public static IWechatOp WechatOp = null; // wechat处理类
	public static String LoginPage = null; // 未知用户登录界面
	
	static{
		try{
			Properties pps = new Properties();
			InputStream in = WechatCommons.class.getClassLoader().getResourceAsStream("wechat");
			pps.load(in);
			AppId = pps.getProperty("AppId");
			AppSecret = pps.getProperty("AppSecret");
			ServerIp = pps.getProperty("ServerIp");
			MchId = pps.getProperty("MchId");
			APIKey = pps.getProperty("APIKey");
			LoginPage = pps.getProperty("LoginPage");
			String opClass = pps.getProperty("OpClass");
			Class cls = Class.forName(opClass);
			WechatOp = (IWechatOp) cls.getDeclaredConstructor(null).newInstance(null);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static final String KeyEchostr = "echostr";
	public static final String KeyText = "text";
	public static final String KeyImage = "image";
	public static final String KeyVoice = "voice";
	public static final String KeyVideo = "video";
	public static final String KeyEvent = "event";
	public static final String KeyEventSubscribe = "subscribe";
	public static final String KeyEventUnsubscribe = "unsubscribe";
	public static final String KeyEventScan = "SCAN";
	public static final String KeySubscribe = "qrscene_";
	
	//获取token
	public static final String Url_Token = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
	                +WechatCommons.AppId+"&secret="+WechatCommons.AppSecret;
	//获取jsTicket
	private static final String Url_JsTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=";
	//发送消息
	private static final String Url_SendMsg = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=";
	//授权
	private static final String Url_Oauth2 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
	           +AppId+"&secret="+AppSecret+"&code={code}&grant_type=authorization_code";
	//创建菜单
	public static final String Url_CreateMenu = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	//删除菜单
	public static final String Url_DeleteMenu = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=";
    //带授权的菜单链接
	private static final String Url_MenuView = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="
          +AppId+"&redirect_uri=http%3A%2F%2F"+ServerIp.replaceAll("/", "%2F")+"%2Fwechatop&response_type=code&scope=snsapi_base&state={state}#wechat_redirect";
	//创建二维码
	public static final String Url_QrCode = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
	//获取二维码
	public static final String Url_ShowQr = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=";
    //用户基本信息
	private static final String Url_UserInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={token}&openid=";
	//统一下单
	public static final String Url_UnifiedOrder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	//多媒体文件下载接口
	public static final String Url_MediaGet = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token={token}&media_id=";
	
	public static String AccessToken = null;
	public static String JsTicket = null;
	
	public static String getUrlJsTicket(){
		return Url_JsTicket + AccessToken;
	}
	
	public static String getUrlSendMsg(){
		return Url_SendMsg + AccessToken;
	}
	
	public static String getUrlOauth2(String code){
		return StringUtils.replaceOnce(Url_Oauth2, "{code}", code);
	}
	
	public static String getUrlView(String state){
		return StringUtils.replaceOnce(Url_MenuView, "{state}", state);
	}
	
	public static String getUrlUserInfo(String openId){
		return StringUtils.replaceOnce(Url_UserInfo, "{token}", AccessToken) + openId;
	}
	
	public static String getUrlMediaGet(String mediaId){
		return StringUtils.replaceOnce(Url_MediaGet, "{token}", AccessToken) + mediaId;
	}
	
	//临时二维码,有效期30天
	public static ReceiveJson createQRCode(long id,String token)throws Exception{
		//永久二维码最多10万个
		//String info = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+id+"}}}";
		String info = "{\"expire_seconds\":2592000,\"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+id+"}}}";
		ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.Url_QrCode+token,WechatCommons.HttpPost,info);
        return json;
	}
}
