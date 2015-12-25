package com.ql.party.service;

import com.ai.appframe2.service.ServiceFactory;
import com.ql.party.service.interfaces.IPartySV;

public class PartyServiceFactory {

	public static IPartySV getPartySV(){
		return (IPartySV)ServiceFactory.getService(IPartySV.class);
	}
}
