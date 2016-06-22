package com.ql.album.service;

import com.ai.appframe2.service.ServiceFactory;
import com.ql.album.service.interfaces.IAlbumSV;

public class AlbumServiceFactory {
	public static IAlbumSV getPartySV(){
		return (IAlbumSV)ServiceFactory.getService(IAlbumSV.class);
	}
}
