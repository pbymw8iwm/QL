package com.ql.album.service.impl;

import java.util.HashMap;

import com.ql.album.bo.AlbumLabelBean;
import com.ql.album.ivalues.IAlbumLabelValue;
import com.ql.album.service.interfaces.IAlbumSV;
import com.ql.sysmgr.QLServiceFactory;

public class AlbumSVImpl implements IAlbumSV{

	/*************************************************
	 * 标签
	 *************************************************/
	/**
	 * 获取个人及系统定义的标签
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IAlbumLabelValue[] getLabels(long userId)throws Exception{
		String cond = "( " + IAlbumLabelValue.S_Userid + " = :userId or "
				+ IAlbumLabelValue.S_Userid + " = -1) and state > 0 order by "
				+ IAlbumLabelValue.S_Groupid + " , "
				+ IAlbumLabelValue.S_Labelid;
		
		HashMap param = new HashMap();
		param.put("userId", userId);
		return (AlbumLabelBean[])QLServiceFactory.getQLDAO().qryDatas(cond, param, AlbumLabelBean.class, AlbumLabelBean.getObjectTypeStatic());
		
	}
	
	/*************************************************
	 * 照片
	 *************************************************/
}
