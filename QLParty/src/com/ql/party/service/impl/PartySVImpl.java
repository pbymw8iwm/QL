package com.ql.party.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;
import com.mysql.jdbc.StringUtils;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.ivalues.IWechatUserValue;
import com.ql.party.bo.CircleMemberBean;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.bo.SocialCircleEngine;
import com.ql.party.ivalues.ICircleMemberValue;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.interfaces.IPartySV;
import com.ql.party.sysmgr.RemoteResouseManager;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.ReceiveJson;
import com.ql.wechat.WechatCommons;

public class PartySVImpl implements IPartySV{

	private static transient Log log = LogFactory.getLog(PartySVImpl.class);
	
	/*************************************************
	 * 圈子
	 *************************************************/

	/**
	 * 保存圈子
	 * @param sc
	 * @return 圈子ID
	 * @throws Exception
	 */
	public long saveSocialCircle(ISocialCircleValue sc)throws Exception{
		boolean isNew = sc.isNew();
		if(isNew){
			//获取二维码，圈子后缀1
			ReceiveJson json = WechatCommons.createQRCode(sc.getCid()*10+1, WechatCommons.AccessToken);
			if(json.isError()){
				log.error("二维码获取失败："+json.getErrMsg());
			}
			else{
				sc.setQrticket(json.getTicket());
				sc.setQrdate(ServiceManager.getOpDateTime());
			}
		}
		QLServiceFactory.getQLDAO().saveData(SocialCircleEngine.transfer(sc));
		//
		if(isNew){
			//如果分布式部署，缓存会有问题
			IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, "0_"+ServiceManager.getUser().getID());
			if(wechatUser == null){
				wechatUser = (IWechatUserValue)ServiceManager.getUser().get("wechatUser");
				ICache cache = (ICache)CacheFactory._getCacheInstances().get(WechatUserCacheImpl.class);
	            try {
					HashMap map = cache.getAll();
					map.put(wechatUser.getOpenid(),wechatUser);
					map.put(wechatUser.getUsertype()+"_"+wechatUser.getUserid(), wechatUser);
					
				} catch (Exception e) {
					log.error(e.getMessage(),e);
				}
			}
			joinSocialCircle(sc.getCid(),wechatUser);
		}
		return sc.getCid();
	}
	
	/**
	 * 加入圈子
	 * @param cId
	 * @param user
	 * @throws Exception
	 */
	public void joinSocialCircle(long cId,IWechatUserValue user)throws Exception{
		//检查是否已加入
		String cond = CircleMemberBean.S_Cid + " = :cId and "
				+ CircleMemberBean.S_Userid + " = :userId and "
				+ CircleMemberBean.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("cId", cId);
		param.put("userId", user.getUserid());
		ICircleMemberValue[] cms = (CircleMemberBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, CircleMemberBean.class, CircleMemberBean.getObjectTypeStatic());
		if(cms != null && cms.length > 0){
			if(log.isDebugEnabled())
				log.debug("用户"+user.getUserid()+"已经加入圈子"+cId);
			return;
		}
		CircleMemberBean cm = new CircleMemberBean();
		cm.setCid(cId);
		cm.setUserid(user.getUserid());
		cm.setUsername(user.getName());
		cm.setCity(user.getCity());
		QLServiceFactory.getQLDAO().saveData(cm);
	}
	
	/**
	 * 根据圈子编号查询圈子
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue getSocialCircle(long cId)throws Exception{
		String cond = ISocialCircleValue.S_Cid + " = :cId ";
		Map param = new HashMap();
		param.put("cId", cId);
		ISocialCircleValue[] sc = (SocialCircleBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, SocialCircleBean.class, SocialCircleBean.getObjectTypeStatic());
		if(sc != null && sc.length > 0){
			//设置圈子类型名称
			sc[0].setExtAttr("TypeName", getCircleTypeName(sc[0].getCtype()+""));
			//设置圈头像
			sc[0].setImagedata("http://"+RemoteResouseManager.Domain+"/c_"+sc[0].getCid()+".jpg");
			//检查二维码是否过期
			if(StringUtils.isNullOrEmpty(sc[0].getQrticket()) 
					|| (ServiceManager.getOpDateTime().getTime() - sc[0].getQrdate().getTime())/3600000 > 29*24){
				//获取二维码，圈子后缀1
				ReceiveJson json = WechatCommons.createQRCode(sc[0].getCid()*10+1, WechatCommons.AccessToken);
				if(json.isError()){
					log.error("二维码获取失败："+json.getErrMsg());
				}
				else{
					sc[0].setQrticket(json.getTicket());
					sc[0].setQrdate(ServiceManager.getOpDateTime());
					QLServiceFactory.getQLDAO().saveData(SocialCircleEngine.transfer(sc[0]));
				}
			}
			return sc[0];
		}
		else
			return null;
	}
	
	/**
	 * 查询用户所有的圈子
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue[] getSocialCircleByUser(long userId)throws Exception{
		String cond = ISocialCircleValue.S_Cid + " in (select a.CId from CircleMember a where a.UserId = :userId and a.State > 0) and "
				+ ISocialCircleValue.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("userId", userId);
		ISocialCircleValue[] scs = (SocialCircleBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, SocialCircleBean.class, SocialCircleBean.getObjectTypeStatic());
		for(ISocialCircleValue sc : scs){
			//设置圈子类型名称
			sc.setExtAttr("TypeName", getCircleTypeName(sc.getCtype()+""));
			//设置圈头像
			sc.setImagedata("http://"+RemoteResouseManager.Domain+"/c_"+sc.getCid()+".jpg");
			//检查二维码是否过期
			if(StringUtils.isNullOrEmpty(sc.getQrticket()) 
					|| (ServiceManager.getOpDateTime().getTime() - sc.getQrdate().getTime())/3600000 > 29*24){
				//获取二维码，圈子后缀1
				ReceiveJson json = WechatCommons.createQRCode(sc.getCid()*10+1, WechatCommons.AccessToken);
				if(json.isError()){
					log.error("二维码获取失败："+json.getErrMsg());
				}
				else{
					sc.setQrticket(json.getTicket());
					sc.setQrdate(ServiceManager.getOpDateTime());
					QLServiceFactory.getQLDAO().saveData(SocialCircleEngine.transfer(sc));
				}
			}
		}
		return scs;
	}
	
	/**
	 * 获取圈子类型
	 * @param cType
	 * @return
	 * @throws Exception
	 */
	private String getCircleTypeName(String cType)throws Exception{
		ICfStaticDataValue[] sds = QLServiceFactory.getQLSV().getStaticDatas("CircleType");
		for(ICfStaticDataValue sd: sds){
			if(sd.getCodevalue().equals(cType))
				return sd.getCodename();
		}
		return "";
	}
	
	/*************************************************
	 * 聚会
	 *************************************************/
}
