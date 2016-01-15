package com.ql.party.service.impl;

import java.text.SimpleDateFormat;
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
import com.ql.party.bo.CircleMemberEngine;
import com.ql.party.bo.PartyBean;
import com.ql.party.bo.PartyEngine;
import com.ql.party.bo.PartyMemberBean;
import com.ql.party.bo.QPartyBean;
import com.ql.party.bo.QPartyEngine;
import com.ql.party.bo.QPartyMemberBean;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.bo.SocialCircleEngine;
import com.ql.party.ivalues.ICircleMemberValue;
import com.ql.party.ivalues.IPartyMemberValue;
import com.ql.party.ivalues.IPartyValue;
import com.ql.party.ivalues.IQPartyMemberValue;
import com.ql.party.ivalues.IQPartyValue;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.PartyServiceFactory;
import com.ql.party.service.interfaces.IPartySV;
import com.ql.party.sysmgr.PartyCommon;
import com.ql.party.sysmgr.RemoteResouseManager;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.ReceiveJson;
import com.ql.wechat.WechatCommons;

public class PartySVImpl implements IPartySV{

	private static transient Log log = LogFactory.getLog(PartySVImpl.class);
	
	/**
	 * 获取二维码的id，原id加后缀
	 * @param id
	 * @param type
	 * @return
	 */
	private long getQrId(long id,int type){
		return id * 10 + type;
	}
	
	/*************************************************
	 * 用户
	 *************************************************/
	/**
	 * 保存用户
	 * @param user
	 * @param isRecover 是否是恢复失效用户
	 * @throws Exception
	 */
	public void saveUser(IWechatUserValue user, boolean isRecover)throws Exception{
		QLServiceFactory.getQLSV().saveUser(user);
		if(isRecover){
			//恢复圈子关系
			String sql = "update CircleMember set state = 1 where UserId = :userId ";
			Map param = new HashMap();
			param.put("userId", user.getUserid());
			QLServiceFactory.getQLDAO().execute(sql, param);
		}
	}
	
	public void deleteUser(IWechatUserValue user)throws Exception{
		if(user == null)
			return;
		//删除圈子关系
		String sql = "update CircleMember set state = 0 where UserId = :userId ";
		Map param = new HashMap();
		param.put("userId", user.getUserid());
		QLServiceFactory.getQLDAO().execute(sql, param);
		//删除用户
		QLServiceFactory.getQLSV().deleteUser(user);
	}
	
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
			long cId = QLServiceFactory.getQLDAO().getNewId(SocialCircleBean.getObjectTypeStatic()).longValue();
			sc.setCid(cId);
			ReceiveJson json = WechatCommons.createQRCode(getQrId(sc.getCid(),PartyCommon.TypeCircle), WechatCommons.AccessToken);
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
	 * 转让圈主
	 * @param cId
	 * @param newMaster
	 * @throws Exception
	 */
	public void changeMaster(long cId,long newMaster)throws Exception{
		SocialCircleBean sc = new SocialCircleBean();
		sc.setCid(cId);
		sc.setStsToOld();
		sc.setCreater(newMaster);
		QLServiceFactory.getQLDAO().saveData(sc);
	}
	
	/**
	 * 检查是否已经加入圈子
	 * @param cId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isJoinedCircle(long cId,long userId)throws Exception{
		//检查是否已加入
		String cond = CircleMemberBean.S_Cid + " = :cId and "
				+ CircleMemberBean.S_Userid + " = :userId and "
				+ CircleMemberBean.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("cId", cId);
		param.put("userId", userId);
		ICircleMemberValue[] cms = (CircleMemberBean[]) QLServiceFactory
				.getQLDAO().qryDatas(cond, param, CircleMemberBean.class,
						CircleMemberBean.getObjectTypeStatic());
		if (cms != null && cms.length > 0) {
			if (log.isDebugEnabled())
				log.debug("用户" + userId + "已经加入圈子" + cId);
			return true;
		}
		return false;
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
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue getSocialCircle(long cId, boolean isExtInfo)throws Exception{
		String cond = ISocialCircleValue.S_Cid + " = :cId ";
		Map param = new HashMap();
		param.put("cId", cId);
		ISocialCircleValue[] sc = (SocialCircleBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, SocialCircleBean.class, SocialCircleBean.getObjectTypeStatic());
		if(sc != null && sc.length > 0){
			setCircleInfo(sc[0],isExtInfo);
			return sc[0];
		}
		else
			return null;
	}
	
	/**
	 * 查询用户所有的圈子
	 * @param userId
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue[] getSocialCirclesByUser(long userId, boolean isExtInfo)throws Exception{
		String cond = ISocialCircleValue.S_Cid + " in (select a.CId from CircleMember a where a.UserId = :userId and a.State > 0) and "
				+ ISocialCircleValue.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("userId", userId);
		ISocialCircleValue[] scs = (SocialCircleBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, SocialCircleBean.class, SocialCircleBean.getObjectTypeStatic());
		for(ISocialCircleValue sc : scs){
			setCircleInfo(sc,isExtInfo);
		}
		return scs;
	}
	
	/**
	 * 用户退出圈子
	 * @param userId
	 * @param cId
	 * @throws Exception
	 */
	public void quitCircle(long userId,long cId)throws Exception{
		CircleMemberBean cm = new CircleMemberBean();
		cm.setCid(cId);
		cm.setUserid(userId);
		cm.setStsToOld();
		cm.delete();
		QLServiceFactory.getQLDAO().saveData(cm);
	}
	
	/**
	 * 获取用户的圈信息
	 * @param cId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ICircleMemberValue getUserCircleInfo(long cId,long userId)throws Exception{
		return CircleMemberEngine.getBean(userId, cId);
	}
	
	/**
	 * 保存个人的圈信息
	 * @param cm
	 * @throws Exception
	 */
	public void saveCircleMemberInfo(ICircleMemberValue cm)throws Exception{
		QLServiceFactory.getQLDAO().saveData(CircleMemberEngine.transfer(cm));
	}
	
	/**
	 * 将用户踢出圈子
	 * @param userIds
	 * @param cId
	 * @throws Exception
	 */
	public void kickoutCircle(long[] userIds,long cId)throws Exception{
		CircleMemberBean[] cms = new CircleMemberBean[userIds.length];
		for(int i=0;i<userIds.length;i++){
			cms[i] = new CircleMemberBean();
			cms[i].setCid(cId);
			cms[i].setUserid(userIds[i]);
			cms[i].setStsToOld();
			cms[i].delete();
		}
		QLServiceFactory.getQLDAO().saveDatas(cms);
	}
	
	/**
	 * 获取圈子的成员数
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public int getCircleMemberCount(long cId)throws Exception{
		String cond = ICircleMemberValue.S_Cid + " = :cId and "
				+ ICircleMemberValue.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("cId", cId);
		return QLServiceFactory.getQLDAO().getCount(cond, param, CircleMemberBean.getObjectTypeStatic());
	}
	
	/**
	 * 查询圈友
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public ICircleMemberValue[] getCircleMembers(long cId)throws Exception{
		String cond = ICircleMemberValue.S_Cid + " = :cId and "
				+ ICircleMemberValue.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("cId", cId);
		ICircleMemberValue[] cms = (CircleMemberBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, CircleMemberBean.class, CircleMemberBean.getObjectTypeStatic());
		for(ICircleMemberValue cm : cms){
			IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, "0_"+cm.getUserid());
			if(wechatUser != null)
				cm.setExtAttr("ImageData", wechatUser.getImagedata());
		}
		return cms;
	}
	
	/**
	 * 设置查询的圈子的附加信息
	 * @param sc
	 * @throws Exception
	 */
	private void setCircleInfo(ISocialCircleValue sc, boolean isExtInfo)throws Exception{
		//设置圈头像
		sc.setImagedata("http://"+RemoteResouseManager.Domain+"/c_"+sc.getCid()+"-200?_="+Math.random());
		//设置圈子类型名称
		sc.setExtAttr("TypeName", getCircleTypeName(sc.getCtype()+""));
		//检查二维码是否过期
		if(StringUtils.isNullOrEmpty(sc.getQrticket()) 
				|| (ServiceManager.getOpDateTime().getTime() - sc.getQrdate().getTime())/3600000 > 29*24){
			//获取二维码，圈子后缀1
			ReceiveJson json = WechatCommons.createQRCode(getQrId(sc.getCid(),PartyCommon.TypeCircle), WechatCommons.AccessToken);
			if(json.isError()){
				log.error("二维码获取失败："+json.getErrMsg());
			}
			else{
				sc.setQrticket(json.getTicket());
				sc.setQrdate(ServiceManager.getOpDateTime());
				QLServiceFactory.getQLDAO().saveData(SocialCircleEngine.transfer(sc));
			}
		}
		if(isExtInfo == false)
			return;
		//查询成员数
		int countM = getCircleMemberCount(sc.getCid());
		sc.setExtAttr("MemberCount", countM);
		//TODO 查询聚会数
		sc.setExtAttr("PartyCount", 0);
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
	/**
	 * 保存聚会
	 * @param party
	 * @return 聚会ID
	 * @throws Exception
	 */
	public long saveParty(IPartyValue party)throws Exception{
		boolean isNew = party.isNew();
		if(isNew){
			//获取二维码，聚会后缀1
			long pId = QLServiceFactory.getQLDAO().getNewId(PartyBean.getObjectTypeStatic()).longValue();
			party.setPartyid(pId);
			ReceiveJson json = WechatCommons.createQRCode(getQrId(party.getPartyid(),PartyCommon.TypeParty), WechatCommons.AccessToken);
			if(json.isError()){
				log.error("二维码获取失败："+json.getErrMsg());
			}
			else{
				party.setQrticket(json.getTicket());
				party.setQrdate(ServiceManager.getOpDateTime());
			}
		}
		QLServiceFactory.getQLDAO().saveData(PartyEngine.transfer(party));
		//
		if(isNew){
			joinParty(party.getPartyid(),party.getCid(),ServiceManager.getUser().getID());
		}
		return party.getPartyid();
	}
	
	/**
	 * 是否加入了聚会
	 * @param partyId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isJoinedParty(long partyId,long userId)throws Exception{
		String cond = PartyMemberBean.S_Partyid + " = :partyId and "
				+ PartyMemberBean.S_Userid + " = :userId and "
				+ PartyMemberBean.S_State + " > 0 ";
		Map param = new HashMap();
		param.put("partyId", partyId);
		param.put("userId", userId);
		IPartyMemberValue[] pms = (PartyMemberBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, PartyMemberBean.class, PartyMemberBean.getObjectTypeStatic());
		if(pms != null && pms.length > 0){
			if(log.isDebugEnabled())
				log.debug("用户"+userId+"已经加入聚会"+partyId);
			return true;
		}
		return false;
	}
		
	/**
	 * 加入聚会
	 * @param partyId
	 * @param cId
	 * @param userId
	 * @throws Exception
	 */
	public void joinParty(long partyId,long cId,long userId)throws Exception{
		PartyMemberBean pm = new PartyMemberBean();
		pm.setPartyid(partyId);
		pm.setUserid(userId);
		pm.setCid(cId);
		pm.setPcount(1);
		QLServiceFactory.getQLDAO().saveData(pm);
	}
	
	/**
	 * 设置聚会参与情况
	 * @param partyId
	 * @param userId
	 * @param state
	 * @param count
	 * @throws Exception
	 */
	public void setPartyMember(long partyId,long userId,long state,long count)throws Exception{
		PartyMemberBean pm = new PartyMemberBean();
		pm.setPartyid(partyId);
		pm.setUserid(userId);
		pm.setStsToOld();
		if(state == 0)
			pm.delete();
		else{
			pm.setState(state);
			pm.setPcount(count);
		}
		QLServiceFactory.getQLDAO().saveData(pm);
	}
	
	/**
	 * 根据聚会编号查询聚会
	 * @param partyId
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public IQPartyValue getParty(long partyId, boolean isExtInfo)throws Exception{
		String sql = QPartyBean.getObjectTypeStatic().getMapingEnty();
		sql += " and " + IQPartyValue.S_Partyid + " = :partyId ";
		Map param = new HashMap();
		param.put("partyId", partyId);
		IQPartyValue[] sc = (QPartyBean[])QLServiceFactory.getQLDAO().qryDatasFromSql(sql, param, QPartyBean.class, QPartyBean.getObjectTypeStatic());
		if(sc != null && sc.length > 0){
			setPartyInfo(sc[0],isExtInfo);
			return sc[0];
		}
		else
			return null;
	}
	
	/**
	 * 查询参与聚会的圈友
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public IQPartyMemberValue[] getPartyMembers(long partyId)throws Exception{
		String sql = QPartyMemberBean.getObjectTypeStatic().getMapingEnty();
		sql += " and " + IQPartyMemberValue.S_Partyid + " = :partyId ";
		Map param = new HashMap();
		param.put("partyId", partyId);
		IQPartyMemberValue[] cms = (QPartyMemberBean[])QLServiceFactory.getQLDAO().qryDatasFromSql(sql, param, QPartyMemberBean.class, QPartyMemberBean.getObjectTypeStatic());
		for(IQPartyMemberValue cm : cms){
			IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, "0_"+cm.getUserid());
			if(wechatUser != null)
				cm.setExtAttr("ImageData", wechatUser.getImagedata());
		}
		return cms;
	}
	
	/**
	 * 设置查询的聚会的附加信息
	 * @param party
	 * @throws Exception
	 */
	private void setPartyInfo(IQPartyValue party, boolean isExtInfo)throws Exception{
		//设置圈头像
		party.setImagedata("http://"+RemoteResouseManager.Domain+"/c_"+party.getCid()+"-200?_="+Math.random());
		//处理时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String pTime = df.format(party.getStarttime());
		party.setExtAttr("Start", pTime);
		if(party.getEndtime() != null){
		    pTime += " ~ " + df.format(party.getEndtime());
			party.setExtAttr("End", df.format(party.getEndtime()));
		}
		else
			party.setExtAttr("End", "");
		party.setExtAttr("PTime", pTime);
		if(StringUtils.isNullOrEmpty(party.getGatheringplace()))
			party.setGatheringplace("待定");
		//检查二维码是否过期
		if(StringUtils.isNullOrEmpty(party.getQrticket()) 
				|| (ServiceManager.getOpDateTime().getTime() - party.getQrdate().getTime())/3600000 > 29*24){
			//获取二维码，聚会后缀2
			ReceiveJson json = WechatCommons.createQRCode(getQrId(party.getCid(),PartyCommon.TypeParty), WechatCommons.AccessToken);
			if(json.isError()){
				log.error("二维码获取失败："+json.getErrMsg());
			}
			else{
				party.setQrticket(json.getTicket());
				party.setQrdate(ServiceManager.getOpDateTime());
				QLServiceFactory.getQLDAO().saveData(QPartyEngine.transfer(party));
			}
		}
		if(isExtInfo == false)
			return;
		
		//查询当前用户的参与情况
		long userId = ServiceManager.getUser().getID();
		String cond = IPartyMemberValue.S_Partyid + " = :partyId and "
				+ IPartyMemberValue.S_Userid + " = :userId ";
		Map param = new HashMap();
		param.put("partyId", party.getPartyid());
		param.put("userId", userId);
		IPartyMemberValue[] pms = (PartyMemberBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, PartyMemberBean.class, PartyMemberBean.getObjectTypeStatic());
		if(pms != null && pms.length > 0){
			party.setExtAttr("SelfState", pms[0].getState());
			if(pms[0].getState() == 1)
				  party.setExtAttr("SelfStateName", "参加");
			else if(pms[0].getState() == 2)
				  party.setExtAttr("SelfStateName", "待定");
			party.setExtAttr("SelfCount", pms[0].getPcount());
		}
		else{
			party.setExtAttr("SelfState", 0);
			party.setExtAttr("SelfStateName", "不参加");
			party.setExtAttr("SelfCount", 0);
		}
	}
	
	public static void main(String[] args)throws Exception {
		PartyServiceFactory.getPartySV().getPartyMembers(1);
	}
}
