package com.ql.party.wechat;

import java.io.InputStream;

import com.ql.party.sysmgr.RemoteResouseManager;
import com.ql.party.web.PartyAction;
import com.ql.wechat.ReceiveJson;
import com.ql.wechat.WechatCommons;
import com.ql.wechat.WechatUtils;

public class WechatMgr {

	private static String AccessToken = "S9mLtPy5QoHd6dZws-6NOjkQd1pzRi8UFo-71paGPJnbxcRrm0dImTcDM8wBaEiUNG-hfp5t7uvChcjDVCfUgdekFFB3gfGNUQ1I9-0yLikEDAeAJAPOL";
	public static void createMenu()throws Exception{

        String menu = "{"
        		+"     \"button\":["
        		+"      {"
        		+"           \"name\":\"聚会\","
        		+"           \"sub_button\":["
        		+"           {	"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"创建聚会\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_NewParty)+"\""
        		+"            },"
        		+"           {	"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"当前聚会\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_CurrentParty)+"\""
        		+"            },"
        		+"            {"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"聚会列表\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_PartyList)+"\""
        		+"            }]"
        		+"       },"
        		+"       "
        		+"      {"
        		+"           \"name\":\"圈子\","
        		+"           \"sub_button\":["
        		+"           {	"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"创建圈子\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_NewCircle)+"\""
        		+"            },"
        		+"            {"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"我的圈子\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_CircleList)+"\""
        		+"            }]"
        		+"       },"
        		+"       "
        		+"      {"
        		+"           \"name\":\"帮助\","
        		+"           \"sub_button\":["
        		+"           {	"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"使用说明\","
        		+"               \"url\":\"http://"+WechatCommons.ServerIp+"/help/Help.jsp\""
        		+"            },"
        		+"            {"
        		+"               \"type\":\"view\","
        		+"               \"name\":\"问题反馈\","
        		+"               \"url\":\""+WechatCommons.getUrlView(WechatOpImpl.Type_Feedback)+"\""
        		+"            }]"
        		+"       }"
        		+"     ]"
        		+" }";
        
        System.out.print(menu);
        ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.Url_CreateMenu+AccessToken,WechatCommons.HttpPost,menu);
        System.out.println(json.getErrMsg());
	}
	
	public static void main(String[] args) throws Exception{
//		WechatMgr.createMenu();
		
		//从微信服务器下载
				InputStream is = WechatUtils.httpRequest(WechatCommons.getUrlMediaGet("RJWGpD3eUTy5SPjUutga4y84y49wlcpIwFHNDrQ6b9KYrYBgP4f9h_-iWcFdjLZ_"), WechatCommons.HttpGet, null);
				byte[] datas = PartyAction.readBytes(is);
				//上传到七牛
				String key = RemoteResouseManager.upload(datas, "c_11.jpg");
				System.out.println(key);
	}
}
