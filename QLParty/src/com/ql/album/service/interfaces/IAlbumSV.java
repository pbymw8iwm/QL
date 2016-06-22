package com.ql.album.service.interfaces;

import com.ql.album.ivalues.IAlbumLabelValue;

public interface IAlbumSV {

	/*************************************************
	 * 标签
	 *************************************************/
	
	/**
	 * 获取个人及系统定义的标签
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public IAlbumLabelValue[] getLabels(long userId)throws Exception;
	
	/*************************************************
	 * 照片
	 *************************************************/
}
