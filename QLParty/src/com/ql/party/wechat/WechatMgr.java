package com.ql.party.wechat;

import com.ql.wechat.ReceiveJson;
import com.ql.wechat.WechatCommons;
import com.ql.wechat.WechatUtils;

public class WechatMgr {

	private static String AccessToken = "Z1_FY0ilb9M9THM0_mzZschhWaPFNCV_lEQZTkgdCGGuGoYPDwXklaJE_GTw0oXCnEWV4wJ2Xw0ZQKmRjArhglXgoQ5xxDyQU69Ebrq5FqJ2r39HRFJVRfWmNFBC8TzRTYZgAGAEJK";
	public static void createMenu()throws Exception{

        String menu = "{"
        		+"     \"button\":["
        		+"            {"
        		+"               \"type\":\"view\","
                +"               \"name\":\"我的相册\","
                +"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_Album)+"\""
                +"            },"
        		+"            {"
        		+"               \"type\":\"view\","
                +"               \"name\":\"聚会助手\","
                +"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_Party)+"\""
                +"            }"
        		+"      ]"
        		+" }";
        
        System.out.print(menu);
        ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.Url_CreateMenu+AccessToken,WechatCommons.HttpPost,menu);
        System.out.println(json.getErrMsg());
	}
	
	public static void main(String[] args) throws Exception{
		WechatMgr.createMenu();
		
		//从微信服务器下载
				/*InputStream is = WechatUtils.httpRequest(WechatCommons.getUrlMediaGet("RJWGpD3eUTy5SPjUutga4y84y49wlcpIwFHNDrQ6b9KYrYBgP4f9h_-iWcFdjLZ_"), WechatCommons.HttpGet, null);
				byte[] datas = CommonUtil.readBytes(is);
				//上传到七牛
				String key = RemoteResouseManager.upload(datas, "c_11.jpg");
				System.out.println(key);*/
	}
}
