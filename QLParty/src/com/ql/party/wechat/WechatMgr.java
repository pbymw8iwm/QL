package com.ql.party.wechat;

import com.ql.wechat.ReceiveJson;
import com.ql.wechat.WechatCommons;
import com.ql.wechat.WechatUtils;

public class WechatMgr {

	private static String AccessToken = "lmNR10sa24hktoY-Rtmvgphnv4RBAyB7Lg6SZWxSnlQnWWjZVBhBFQAhesDyGFcMqp5K-uGglDl9StWlwwIj1qwhTzLGimvdrYv-HL8COG8TGGbAIAJWW";
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
        ReceiveJson json = WechatUtils.httpRequest(WechatCommons.Url_CreateMenu+AccessToken,WechatCommons.HttpPost,menu);
        System.out.println(json.getErrMsg());
	}
	
	public static void main(String[] args) throws Exception{
		WechatMgr.createMenu();
	}
}
