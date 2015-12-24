package com.ql.dao.interfaces;

import java.math.BigDecimal;
import java.util.Map;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ObjectType;

public interface IQLDAO {

	///////////////////////////////////////////////////
	/** 公共方法  **/
	///////////////////////////////////////////////////
	public BigDecimal getNewId(ObjectType ot) throws Exception;
	
	public void saveData(DataContainerInterface dc) throws Exception;
	
	public void saveDatas(DataContainerInterface[] dcs) throws Exception;
	
	public void deleteDatas(DataContainerInterface[] dcs) throws Exception;
	
	public DataContainerInterface[] qryDatas(String cond,Map param,Class aClass,ObjectType ot)throws Exception;
	
	public DataContainerInterface[] qryDatasFromSql(String sql, Map param, Class aClass, ObjectType ot) throws Exception;

}
