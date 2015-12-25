package com.ql.party.service.interfaces;

import com.ql.ivalues.IWechatUserValue;
import com.ql.party.ivalues.ISocialCircleValue;

public interface IPartySV {

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
	 * 加入圈子
	 * @param cId
	 * @param user
	 * @throws Exception
	 */
	public void joinSocialCircle(long cId,IWechatUserValue user)throws Exception;
	
	/**
	 * 根据圈子编号查询圈子
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue getSocialCircle(long cId)throws Exception;
	
	/**
	 * 查询用户所有的圈子
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ISocialCircleValue[] getSocialCircleByUser(long userId)throws Exception;
	
	
	
	/*************************************************
	 * 聚会
	 *************************************************/
}
