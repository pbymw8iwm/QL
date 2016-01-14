package com.ql.party.service.interfaces;

import com.ql.ivalues.IWechatUserValue;
import com.ql.party.ivalues.ICircleMemberValue;
import com.ql.party.ivalues.IPartyValue;
import com.ql.party.ivalues.IQPartyMemberValue;
import com.ql.party.ivalues.IQPartyValue;
import com.ql.party.ivalues.ISocialCircleValue;

public interface IPartySV {

	/*************************************************
	 * 用户
	 *************************************************/
	/**
	 * 保存用户
	 * @param user
	 * @param isRecover 是否是恢复失效用户
	 * @throws Exception
	 */
	public void saveUser(IWechatUserValue user, boolean isRecover)throws Exception;
	
	public void deleteUser(IWechatUserValue user)throws Exception;

	/*************************************************
	 * 圈子
	 *************************************************/
	
	/**
	 * 保存圈子
	 * @param sc
	 * @return 圈子ID
	 * @throws Exception
	 */
	public long saveSocialCircle(ISocialCircleValue sc)throws Exception;
	
	/**
	 * 转让圈主
	 * @param cId
	 * @param newMaster
	 * @throws Exception
	 */
	public void changeMaster(long cId,long newMaster)throws Exception;
	
	/**
	 * 检查是否已经加入圈子
	 * @param cId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isJoinedCircle(long cId,long userId)throws Exception;
	
	/**
	 * 加入圈子
	 * @param cId
	 * @param user
	 * @throws Exception
	 */
	public void joinSocialCircle(long cId,IWechatUserValue user)throws Exception;
	
	/**
	 * 根据圈子编号查询圈子
	 * @param cId
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue getSocialCircle(long cId, boolean isExtInfo)throws Exception;
	
	/**
	 * 查询用户所有的圈子
	 * @param userId
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue[] getSocialCirclesByUser(long userId, boolean isExtInfo)throws Exception;

	/**
	 * 用户退出圈子
	 * @param userId
	 * @param cId
	 * @throws Exception
	 */
	public void quitCircle(long userId,long cId)throws Exception;
	
	/**
	 * 获取用户的圈信息
	 * @param cId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ICircleMemberValue getUserCircleInfo(long cId,long userId)throws Exception;
	
	/**
	 * 保存个人的圈信息
	 * @param cm
	 * @throws Exception
	 */
	public void saveCircleMemberInfo(ICircleMemberValue cm)throws Exception;

	/**
	 * 将用户踢出圈子
	 * @param userIds
	 * @param cId
	 * @throws Exception
	 */
	public void kickoutCircle(long[] userIds,long cId)throws Exception;
	
	/**
	 * 获取圈子的成员数
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public int getCircleMemberCount(long cId)throws Exception;

	/**
	 * 查询圈友
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public ICircleMemberValue[] getCircleMembers(long cId)throws Exception;
	
	
	/*************************************************
	 * 聚会
	 *************************************************/
	/**
	 * 保存聚会
	 * @param party
	 * @return 聚会ID
	 * @throws Exception
	 */
	public long saveParty(IPartyValue party)throws Exception;
	/**
	 * 是否加入了聚会
	 * @param partyId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean isJoinedParty(long partyId,long userId)throws Exception;
	
	/**
	 * 加入聚会
	 * @param partyId
	 * @param cId
	 * @param userId
	 * @throws Exception
	 */
	public void joinParty(long partyId,long cId,long userId)throws Exception;
	
	/**
	 * 设置聚会参与情况
	 * @param partyId
	 * @param userId
	 * @param state
	 * @param count
	 * @throws Exception
	 */
	public void setPartyMember(long partyId,long userId,long state,long count)throws Exception;
	
	/**
	 * 根据聚会编号查询聚会
	 * @param partyId
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public IQPartyValue getParty(long partyId, boolean isExtInfo)throws Exception;
	/**
	 * 查询参与聚会的圈友
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public IQPartyMemberValue[] getPartyMembers(long partyId)throws Exception;
}
