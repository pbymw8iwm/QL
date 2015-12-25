package com.ql.cache;

import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ql.ivalues.IWechatUserValue;
import com.ql.sysmgr.QLServiceFactory;

public class WechatUserCacheImpl extends AbstractCache{

	public HashMap getData() throws Exception {
		HashMap map = new HashMap();
		
		IWechatUserValue[] datas = QLServiceFactory.getQLSV().getWechatUsers();
		for(int i=0;i<datas.length;i++){
			map.put(datas[i].getOpenid(), datas[i]);
			map.put(datas[i].getUsertype()+"_"+datas[i].getUserid(), datas[i]);
		}
		return map;
		
	}
}
