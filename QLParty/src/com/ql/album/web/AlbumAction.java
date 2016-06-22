package com.ql.album.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.SessionManager;
import com.ql.action.QLBaseAction;
import com.ql.album.ivalues.IAlbumLabelValue;
import com.ql.album.service.AlbumServiceFactory;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.sysmgr.QLServiceFactory;

public class AlbumAction extends QLBaseAction{
	private static final Log log = LogFactory.getLog(AlbumAction.class);

	/*************************************************
	 * jsp上直接调用的方法
	 *************************************************/
	public static ICfStaticDataValue[] getLabelGroups()throws Exception{
		return QLServiceFactory.getQLSV().getStaticDatas("LabelGroup");
	}
	
	public static IAlbumLabelValue[] getLabels()throws Exception{
		return AlbumServiceFactory.getPartySV().getLabels(SessionManager.getUser().getID());
	}
	
	/*************************************************
	 * action
	 *************************************************/
}
