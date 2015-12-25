package com.ql.service.interfaces;

import com.ql.ivalues.ICfStaticDataValue;
import com.ql.ivalues.IWechatUserValue;

public interface IQLSV {
	
	/**
	 * 获取所有的配置数据
	 * @return
	 * @throws Exception
	 */
	public ICfStaticDataValue[] getAllStaticDatas()throws Exception;
	
	/**
	 * 获取指定类型的配置数据
	 * @param codeType
	 * @return
	 * @throws Exception
	 */
	public ICfStaticDataValue[] getStaticDatas(String codeType)throws Exception;

	/**
	 * 获取有效的用户
	 * @return
	 * @throws Exception
	 */
	public IWechatUserValue[] getWechatUsers()throws Exception;

	/**
	 * 获取用户
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public IWechatUserValue getUser(String openId)throws Exception;
	
	/**
	 * 保存微信用户
	 * @param user
	 * @throws Exception
	 */
	public void saveUser(IWechatUserValue user)throws Exception;
	
	/**
	 * 删除微信用户
	 * @param user
	 * @throws Exception
	 */
	public void deleteUser(IWechatUserValue user)throws Exception;
}
