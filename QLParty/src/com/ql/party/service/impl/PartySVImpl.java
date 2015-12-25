package com.ql.party.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.complex.cache.ICache;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.ivalues.IWechatUserValue;
import com.ql.party.bo.CircleMemberBean;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.bo.SocialCircleEngine;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.interfaces.IPartySV;
import com.ql.sysmgr.QLServiceFactory;

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
			sc[0].setExtAttr("TypeName", getCircleTypeName(sc[0].getCtype()+""));
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
		for(ISocialCircleValue sc : scs)
			sc.setExtAttr("TypeName", getCircleTypeName(sc.getCtype()+""));
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
