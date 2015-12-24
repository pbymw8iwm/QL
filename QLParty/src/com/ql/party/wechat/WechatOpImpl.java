package com.ql.party.wechat;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;
import com.ql.bo.WechatUserBean;
import com.ql.ivalues.IWechatUserValue;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.IWechatOp;
import com.ql.wechat.ReceiveJson;
import com.ql.wechat.ReceiveXmlEntity;
import com.ql.wechat.WechatCommons;
import com.ql.wechat.WechatUserCacheImpl;
import com.ql.wechat.WechatUtils;

public class WechatOpImpl implements IWechatOp {

	private static transient Log log = LogFactory.getLog(WechatOpImpl.class);
	/**
	 * 处理订阅事件
	 * @param xmlEntity
	 * @return
	 */
	public String processSubscribe(ReceiveXmlEntity xmlEntity){
		String openId = xmlEntity.getFromUserName();
		if(log.isDebugEnabled())
			log.debug("订阅："+openId);
		IWechatUserValue wechatUser = null;
		String result = null;
        try{
        	//获取微信用户信息
        	ReceiveJson json = WechatUtils.httpRequest(WechatCommons.getUrlUserInfo(openId), WechatCommons.HttpGet, null);
        	if(json.isError()){
        		log.error("用户信息("+openId+")获取失败:"+json.getErrMsg());
        		json = null;
        	}
        	
        	wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, openId);
        	
            if(wechatUser == null){
            	//检查是否是之前退订过的
            	wechatUser = QLServiceFactory.getQLSV().getUser(openId);
            }
            if(wechatUser.isNew()){
            	result = "欢迎使用聚会助手! 初次使用可查看帮助";
            }
            else if(wechatUser.getState() == 0){
        		wechatUser.setState(1);
        		result = "欢迎回来^_^";
        	}
            if(wechatUser.isNew() || wechatUser.isModified()){
	            if(json != null){
	            	wechatUser.setName(json.getNickname());
	            	wechatUser.setGender(json.getSex());
	            	wechatUser.setCity(json.getProvince()+json.getCity());
	            	wechatUser.setImagedata(json.getHeadimgurl());
	            }
	            //保存
	            QLServiceFactory.getQLSV().saveUser(wechatUser);
	            //刷缓存
	            ICache cache = (ICache)CacheFactory._getCacheInstances().get(WechatUserCacheImpl.class);
	            try {
					HashMap map = cache.getAll();
					map.put(openId,wechatUser);
					map.put(wechatUser.getUsertype()+"_"+wechatUser.getUserid(), wechatUser);
					
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
            }
        }
        catch(Exception ex){
        	log.error(ex.getMessage(),ex);
        }
		return result;
	}

	/**
	 * 处理退订事件
	 * @param xmlEntity
	 * @return
	 */
	public String processUnsubscribe(ReceiveXmlEntity xmlEntity){
		String openId = xmlEntity.getFromUserName();
		if(log.isDebugEnabled())
			log.debug("退订："+openId);
		IWechatUserValue wechatUser = null;
		try {
			wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, openId);
			if(wechatUser != null){
				QLServiceFactory.getQLSV().deleteUser(wechatUser);
	            //刷缓存
	            ICache cache = (ICache)CacheFactory._getCacheInstances().get(WechatUserCacheImpl.class);
	            try {
					HashMap map = cache.getAll();
					map.remove(openId);
					map.remove(wechatUser.getUsertype()+"_"+wechatUser.getUserid());
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		} catch (Exception ex) {
        	log.error(ex.getMessage(),ex);
		}
		return "感谢使用, 期待您的再次到来!";
	}
	
	/**
	 * 处理带参二维码扫描
	 * @param xmlEntity
	 * @return
	 */
	public String processScan(ReceiveXmlEntity xmlEntity){
		return "欢迎使用聚会助手！";
	}
	
	/**
	 * 处理普通消息
	 * @param xmlEntity
	 * @return
	 */
	public String processMsg(ReceiveXmlEntity xmlEntity){
		return "欢迎使用聚会助手！";
	}

	/**
	 * 处理带授权的菜单链接
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	public String getOpUrl(HttpServletRequest request, HttpServletResponse response, String param){
		String url = null;
		if(Type_NewParty.equals(param))
			url = "party/NewParty.jsp";
		else if(Type_CurrentParty.equals(param))
			url = "party/CurrentParty.jsp";
		else if(Type_PartyList.equals(param))
			url = "party/PartyList.jsp";
		else if(Type_NewCircle.equals(param))
			url = "circle/NewCircle.jsp";
		else if(Type_CircleList.equals(param))
			url = "circle/CircleList.jsp";
		else if(Type_Feedback.equals(param))
			url = "help/Feedback.jsp";
		return url;
	}
	
	public static final String Type_NewParty = "1";
	public static final String Type_CurrentParty = "2";
	public static final String Type_PartyList = "3";
	
	public static final String Type_NewCircle = "11";
	public static final String Type_CircleList = "12";

	public static final String Type_Feedback = "99";
}
