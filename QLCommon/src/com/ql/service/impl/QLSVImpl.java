package com.ql.service.impl;

import com.ql.bo.WechatUserBean;
import com.ql.bo.WechatUserEngine;
import com.ql.ivalues.IWechatUserValue;
import com.ql.service.interfaces.IQLSV;
import com.ql.sysmgr.QLServiceFactory;

public class QLSVImpl implements IQLSV{

	/**
	 * 获取有效的用户
	 * @return
	 * @throws Exception
	 */
	public IWechatUserValue[] getWechatUsers()throws Exception{
		return (WechatUserBean[])QLServiceFactory.getQLDAO().qryDatas(IWechatUserValue.S_State + " > 0 ", null, WechatUserBean.class, WechatUserBean.getObjectTypeStatic());
	}
	
	/**
	 * 获取用户
	 * @param openId
	 * @return
	 * @throws Exception
	 */
	public IWechatUserValue getUser(String openId)throws Exception{
		return WechatUserEngine.getBean(openId);
	}
	
	/**
	 * 保存微信用户
	 * @param user
	 * @throws Exception
	 */
	public void saveUser(IWechatUserValue user)throws Exception{
		if(user.isNew()){
			if(user.getUserid() == 0)
				user.setUserid(QLServiceFactory.getQLDAO().getNewId(WechatUserBean.getObjectTypeStatic()).longValue());
		}
		QLServiceFactory.getQLDAO().saveData(WechatUserEngine.transfer(user));
	}
	
	/**
	 * 删除微信用户
	 * @param user
	 * @throws Exception
	 */
	public void deleteUser(IWechatUserValue user)throws Exception{
		user.setState(0);
		QLServiceFactory.getQLDAO().saveData(WechatUserEngine.transfer(user));
	}
}
