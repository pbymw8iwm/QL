package com.ql.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.ql.dao.interfaces.IQLDAO;

public class QLDAOImpl implements IQLDAO {

	///////////////////////////////////////////////////
	/** 公共方法  **/
	///////////////////////////////////////////////////
	public BigDecimal getNewId(ObjectType ot) throws Exception {
		Connection conn = ServiceManager.getSession().getConnection();
		BigDecimal result = null;
		try {
			result = ServiceManager.getIdGenerator().getNewId(conn, ot);
		} finally {
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public void saveData(DataContainerInterface dc) throws Exception {
		Connection conn = ServiceManager.getSession().getConnection();
		try {
			Map keyPropertys = dc.getKeyProperties();
			Set keys = keyPropertys.keySet();
			if (dc.isNew()) {
				// 设置主键
				for (Iterator it = keys.iterator(); it.hasNext();) {
					String key = (String) it.next();
					// key为空，则取newId
					if (dc.get(key) == null) {
						dc.set(key,
								ServiceManager.getIdGenerator().getNewId(conn,
										dc.getObjectType()));
					}
				}
				if (dc.getObjectType().hasProperty("STATE")) {
					// 设置状态
					if (dc.get("STATE") == null)
						dc.set("STATE", Integer.valueOf(1));
				}
			} 
//			else if (dc.isDeleted()) {
//				 dc.setStsToOld();
//				 dc.set("STATE", new Integer(0));
//			}
			ServiceManager.getDataStore().save(conn, dc);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void saveDatas(DataContainerInterface[] dcs) throws Exception {
		if (dcs == null)
			return;
		Connection conn = ServiceManager.getSession().getConnection();
		try {
			for (int i = 0; i < dcs.length; i++) {
				Map keyPropertys = dcs[i].getKeyProperties();
				Set keys = keyPropertys.keySet();

				if (dcs[i].isNew()) {
					// 设置主键
					for (Iterator it = keys.iterator(); it.hasNext();) {
						String key = (String) it.next();
						// key为空，则取newId
						if (dcs[i].get(key) == null) {
							dcs[i].set(key, ServiceManager.getIdGenerator()
									.getNewId(conn, dcs[i].getObjectType()));
						}
					}

					if (dcs[i].getObjectType().hasProperty("STATE")) {
						// 设置状态
						if (dcs[i].get("STATE") == null)
							dcs[i].set("STATE", Integer.valueOf(1));
					}
				} 
//				else if (dcs[i].isDeleted()) {
//					 dcs[i].setStsToOld();
//					 dcs[i].set("STATE", new Integer(0));
//				}
			}
			ServiceManager.getDataStore().saveBatch(conn, dcs);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public void deleteDatas(DataContainerInterface[] dcs) throws Exception {
		if (dcs == null)
			return;
		for (int i = 0; i < dcs.length; i++) {
			// dcs[i].setStsToOld();
			// dcs[i].set("STATE", new Integer(0));
			dcs[i].delete();
		}
		Connection conn = ServiceManager.getSession().getConnection();
		try {
			ServiceManager.getDataStore().saveBatch(conn, dcs);
		} finally {
			if (conn != null)
				conn.close();
		}
	}

	public DataContainerInterface[] qryDatas(String cond,Map param,Class aClass,ObjectType ot)throws Exception{
		Connection conn = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			return ServiceManager.getDataStore().retrieve(conn,aClass,ot,null,cond,param,-1,-1,false,false,null);
		}
		finally{
		   if (conn != null)
		      conn.close();
		}	
	}

	public DataContainerInterface[] qryDatasFromSql(String sql, Map param, Class aClass, ObjectType ot) throws Exception {
		Connection conn = null;
		ResultSet resultset = null;
		try {
			conn = ServiceManager.getSession().getConnection();
			resultset = ServiceManager.getDataStore().retrieve(conn, sql, param);
			return ServiceManager.getDataStore().crateDtaContainerFromResultSet(aClass, ot, resultset, null, true);
		} catch (Exception e) {
			throw e;
		} finally {
			if (resultset != null)
				resultset.close();
			if (conn != null)
				conn.close();
		}
	}

}
