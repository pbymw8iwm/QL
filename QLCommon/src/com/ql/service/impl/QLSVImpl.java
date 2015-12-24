package com.ql.service.impl;

import com.ql.bo.WechatUserBean;
import com.ql.ivalues.IWechatUserValue;
import com.ql.service.interfaces.IQLSV;
import com.ql.sysmgr.QLServiceFactory;

public class QLSVImpl implements IQLSV{

	public IWechatUserValue[] getWechatUsers()throws Exception{
		return (WechatUserBean[])QLServiceFactory.getQLDAO().qryDatas(IWechatUserValue.S_State + " > 0 ", null, WechatUserBean.class, WechatUserBean.getObjectTypeStatic());
	}
}
