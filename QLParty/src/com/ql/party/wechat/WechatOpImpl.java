package com.ql.party.wechat;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.DataType;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.ivalues.IWechatUserValue;
import com.ql.party.ivalues.IQPartyValue;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.PartyServiceFactory;
import com.ql.party.sysmgr.PartyCommon;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.IWechatOp;
import com.ql.wechat.ReceiveJson;
import com.ql.wechat.ReceiveXmlEntity;
import com.ql.wechat.WechatCommons;
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
		boolean isRecover = false;
        try{        	
        	wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, openId);
        	
            if(wechatUser == null){
            	//检查是否是之前退订过的
            	wechatUser = QLServiceFactory.getQLSV().getUser(openId);
            }
            if(wechatUser.isNew()){
            	result = "欢迎使用汇聚时光! \n\n将照片传至【我的相册】，分组管理，快速查找；不占用手机空间，且永不丢失 \n\n使用【聚会助手】，不错过亲友聚会的精彩";
            }
            else if(wechatUser.getState() == 0){
        		wechatUser.setState(1);
        		isRecover = true;
        		result = "欢迎回来^_^";
        	}
            if(wechatUser.isNew() || wechatUser.isModified()){
            	//获取微信用户信息
            	ReceiveJson json = WechatUtils.httpRequestJson(WechatCommons.getUrlUserInfo(openId), WechatCommons.HttpGet, null);
            	if(json.isError()){
            		log.error("用户信息("+openId+")获取失败:"+json.getErrMsg());
            		json = null;
            	}
	            if(json != null){
	            	wechatUser.setName(json.getNickname());
	            	wechatUser.setGender(json.getSex());
	            	wechatUser.setCity(json.getProvince()+json.getCity());
	            	wechatUser.setImagedata(json.getHeadimgurl());
	            }
	            //保存
	            PartyServiceFactory.getPartySV().saveUser(wechatUser,isRecover);
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
            
            //扫描的带参二维码
            if(xmlEntity.getEventKey() != null && xmlEntity.getEventKey().startsWith(WechatCommons.KeySubscribe)){
            	String param = xmlEntity.getEventKey().substring(WechatCommons.KeySubscribe.length());
            	String rt = dealScan(param,wechatUser);
            	if(rt != null){
            		if(result == null)
            			result = rt;
            		else
            			result += "\n\n"+rt;
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
				PartyServiceFactory.getPartySV().deleteUser(wechatUser);
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
		String param = xmlEntity.getEventKey();
		IWechatUserValue wechatUser = null;
		String result = null;
        try{
        	wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, xmlEntity.getFromUserName());
        	//如果在应用停止的时候关注的，就会只关注而没有创建用户，所以这样要加个判断
        	if(wechatUser == null){
        		result = processSubscribe(xmlEntity);
        		wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, xmlEntity.getFromUserName());
        	}
        	if(wechatUser != null)
        		result = dealScan(param,wechatUser);
        }
        catch(Exception ex){
        	log.error(ex.getMessage(),ex);
        }
        if(result == null)
        	result = "欢迎使用汇聚时光! \n\n将照片传至【我的相册】，分组管理，快速查找；不占用手机空间，且永不丢失 \n\n使用【聚会助手】，不错过亲友聚会的精彩";
		return result;
	}
	
	/**
	 * 处理普通消息
	 * @param xmlEntity
	 * @return
	 */
	public String processMsg(ReceiveXmlEntity xmlEntity){
		return "将照片传至【我的相册】，分组管理，快速查找；不占用手机空间，且永不丢失 \n\n使用【聚会助手】，不错过亲友聚会的精彩";
	}
	
	private String dealScan(String param,IWechatUserValue user)throws Exception{
		long id = DataType.getAsLong(param);
		long index = id%10;
		if(index == PartyCommon.TypeCircle){
			//圈子
			long cId = id/10;
			if(PartyServiceFactory.getPartySV().isJoinedCircle(cId, user.getUserid()) == false)
				PartyServiceFactory.getPartySV().joinSocialCircle(cId, user);
			ISocialCircleValue sc = PartyServiceFactory.getPartySV().getSocialCircle(cId, false);
			return "您加入了圈子【"+sc.getCname()+"】，\n请点击<a href='"+WechatCommons.getUrlView(WechatOpImpl.Type_CircleInfo+cId)+"'>这里</a>进入";
		}
		else if(index == PartyCommon.TypeParty){
			//聚会
			long partyId = id/10;
			long userId = user.getUserid();
			IQPartyValue party = PartyServiceFactory.getPartySV().getParty(partyId, false);
			if(PartyServiceFactory.getPartySV().isJoinedParty(partyId, userId) == false){
				long cId = party.getCid();
				//没有加入圈子则加入
				if(PartyServiceFactory.getPartySV().isJoinedCircle(cId, userId) == false)
					PartyServiceFactory.getPartySV().joinSocialCircle(cId, user);
				PartyServiceFactory.getPartySV().joinParty(partyId, cId, userId);
			}
			return "您加入了"+party.getUsername()+"发起的聚会【"+party.getTheme()+"】，\n请点击<a href='"+WechatCommons.getUrlView(WechatOpImpl.Type_PartyInfo+partyId)+"'>这里</a>进入";
			
		}
		return null;
	}

	/**
	 * 处理带授权的菜单链接
	 * @param param
	 * @param wechatUser
	 * @return
	 */
	public String getOpUrl(String param, IWechatUserValue wechatUser){
		String url = null;
		if(param.equals(Type_Album)){
			url = "album/Main.jsp";
		}
		else{
			url = "party/Main.jsp";
			if(param.equals(Type_Party)){
				
			}
			else if(param.startsWith(Type_CircleInfo)){
				String strCId = param.substring(Type_CircleInfo.length());
	        	url += "?cId="+strCId+"#ci";
			}
			else if(param.startsWith(Type_PartyInfo)){
				String strPId = param.substring(Type_PartyInfo.length());
	        	url += "?partyId="+strPId+"#pi";
			}
			else if(param.startsWith(Type_JoinCircle)){
				String strCId = param.substring(Type_JoinCircle.length());
				try {
					boolean isM = PartyServiceFactory.getPartySV().isJoinedCircle(Long.parseLong(strCId), wechatUser.getUserid());
					//已经加入圈子
					if(isM)
						url += "?cId="+strCId+"#ci";
					else
						url = "party/circle/CircleQR.jsp?cId="+strCId;
				} 
				catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			else if(param.startsWith(Type_JoinParty)){
				String strPId = param.substring(Type_JoinParty.length());
				try {
					boolean isM = PartyServiceFactory.getPartySV().isJoinedParty(Long.parseLong(strPId), wechatUser.getUserid());
					//已经加入聚会
					if(isM)
						url += "?partyId="+strPId+"#pi";
					else
						url = "party/party/PartyQR.jsp?partyId="+strPId;
				} 
				catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return url;
	}
	
	/**
	 * 处理未知用户的带授权菜单链接
	 * @param param
	 * @return
	 */
	public String getNoUserOpUrl(String param){
		String url = null;
		if(param.startsWith(Type_JoinCircle)){
			String strCId = param.substring(Type_JoinCircle.length());
        	url = "party/circle/CircleQR.jsp?cId="+strCId;
		}
		else if(param.startsWith(Type_JoinParty)){
			String strPId = param.substring(Type_JoinParty.length());
        	url = "party/party/PartyQR.jsp?partyId="+strPId;
		}
		return url;
	}
	
	public static final String Type_Album = "1";

	public static final String Type_Party = "2";
	public static final String Type_PartyInfo = "21_";
	public static final String Type_JoinParty = "22_";
	public static final String Type_CircleInfo = "23_";
	public static final String Type_JoinCircle = "24_";

	public static final String Type_Feedback = "99";
}
