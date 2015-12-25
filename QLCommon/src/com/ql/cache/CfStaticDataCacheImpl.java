package com.ql.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.ai.appframe2.complex.cache.impl.AbstractCache;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.sysmgr.QLServiceFactory;

public class CfStaticDataCacheImpl extends AbstractCache{
	public HashMap getData() throws Exception {
		HashMap map = new HashMap();
		
		ICfStaticDataValue[] datas = QLServiceFactory.getQLSV().getAllStaticDatas();
		String lastType = null;
		ArrayList<ICfStaticDataValue> list = null;
		for(int i=0;i<datas.length;i++){
			if(datas[i].getCodetype().equals(lastType)){
				list.add(datas[i]);
			}
			else{
				if(lastType != null){
					map.put(lastType,list.toArray(new ICfStaticDataValue[0]));
				}
				list = new ArrayList<ICfStaticDataValue>();
				list.add(datas[i]);
				lastType = datas[i].getCodetype();
			}
		}
		if(lastType != null){
			map.put(lastType,list.toArray(new ICfStaticDataValue[0]));
		}
		return map;
		
	}
}
