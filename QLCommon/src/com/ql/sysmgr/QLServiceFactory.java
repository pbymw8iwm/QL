package com.ql.sysmgr;

import com.ai.appframe2.service.ServiceFactory;
import com.ql.dao.interfaces.IQLDAO;
import com.ql.service.interfaces.IQLSV;

public class QLServiceFactory {

	public static IQLSV getQLSV(){
		return (IQLSV)ServiceFactory.getService(IQLSV.class);
	}

	public static IQLDAO getQLDAO(){
		return (IQLDAO)ServiceFactory.getService(IQLDAO.class);
	}
}
