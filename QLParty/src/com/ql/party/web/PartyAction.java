package com.ql.party.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.web.HttpUtil;
import com.ql.action.QLBaseAction;
import com.ql.common.HttpJsonUtil;
import com.ql.common.JsonUtil;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.PartyServiceFactory;
import com.ql.sysmgr.QLServiceFactory;

public class PartyAction extends QLBaseAction{
	private static final Log log = LogFactory.getLog(PartyAction.class);

	
	/*************************************************
	 * jsp上直接调用的方法
	 *************************************************/
	public static ICfStaticDataValue[] getStaticDatas(String codeType)throws Exception{
		return QLServiceFactory.getQLSV().getStaticDatas(codeType);
	}

	/**
	 * 查询圈子
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public static ISocialCircleValue getSocialCircle(long cId)throws Exception{
		return PartyServiceFactory.getPartySV().getSocialCircle(cId);
	}

	/*************************************************
	 * action
	 *************************************************/
	
	//创建圈子
	public void createCircle(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String json = HttpUtil.getStringFromBufferedReader(request);
			Map map = JsonUtil.getMapFromJsObject(json);
			
			SocialCircleBean sc = new SocialCircleBean();
			JsonUtil.mapToBean(map, sc);
			sc.setCreater(SessionManager.getUser().getID());
			long cId = PartyServiceFactory.getPartySV().saveSocialCircle(sc);
	        HttpJsonUtil.showInfo(response,cId+"");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	
}
