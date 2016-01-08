package com.ql.party.web;

import java.io.InputStream;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.web.HttpUtil;
import com.ql.action.QLBaseAction;
import com.ql.common.CommonUtil;
import com.ql.common.HttpJsonUtil;
import com.ql.common.JsonUtil;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.party.bo.CircleMemberBean;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.ivalues.ICircleMemberValue;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.PartyServiceFactory;
import com.ql.party.sysmgr.RemoteResouseManager;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.WechatCommons;
import com.ql.wechat.WechatUtils;

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
	public static ISocialCircleValue getSocialCircle(long cId,boolean isExtInfo)throws Exception{
		return PartyServiceFactory.getPartySV().getSocialCircle(cId, isExtInfo);
	}
	
	/**
	 * 查询用户的圈子
	 * @param isExtInfo 是否查询附加信息
	 * @return
	 * @throws Exception
	 */
	public static ISocialCircleValue[] getSocialCircles(boolean isExtInfo)throws Exception{
		return PartyServiceFactory.getPartySV().getSocialCirclesByUser(SessionManager.getUser().getID(),isExtInfo);
	}
	
	/**
	 * 查询圈子的成员
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public static ICircleMemberValue[] getCircleMembers(long cId)throws Exception{
		return PartyServiceFactory.getPartySV().getCircleMembers(cId);
	}

	/*************************************************
	 * action
	 *************************************************/
	
	//创建圈子
	public void createCircle(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String mediaId = HttpUtil.getAsString(request, "mediaId");
			String json = HttpUtil.getStringFromBufferedReader(request);
			Map map = JsonUtil.getMapFromJsObject(json);
			
			SocialCircleBean sc = new SocialCircleBean();
			JsonUtil.mapToBean(map, sc);
			sc.setCreater(SessionManager.getUser().getID());
			long cId = PartyServiceFactory.getPartySV().saveSocialCircle(sc);
			remoteImg(cId,mediaId);	
	        HttpJsonUtil.showInfo(response,cId+"");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	//修改圈子的头像
	public void changeCircleImg(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String mediaId = HttpUtil.getAsString(request, "mediaId");
			long cId = HttpUtil.getAsLong(request, "cId");
			remoteImg(cId,mediaId);			
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	//转让圈主
	public void changeMaster(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long cId = HttpUtil.getAsLong(request, "cId");
			long userId = HttpUtil.getAsLong(request, "userId");
			PartyServiceFactory.getPartySV().changeMaster(cId, userId);
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}	
	
	//退出
	public void quitCircle(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long cId = HttpUtil.getAsLong(request, "cId");
			PartyServiceFactory.getPartySV().quitCircle(SessionManager.getUser().getID(), cId);
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	public void getSelfCircleInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long cId = HttpUtil.getAsLong(request, "cId");
			ICircleMemberValue cm = PartyServiceFactory.getPartySV().getUserCircleInfo(cId, SessionManager.getUser().getID());
			HttpJsonUtil.showInfo(response,JsonUtil.getJsonFromBO(cm));
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	//保存个人的圈信息
	public void saveCircleMemberInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String json = HttpUtil.getStringFromBufferedReader(request);
			Map map = JsonUtil.getMapFromJsObject(json);
			
			CircleMemberBean cm = new CircleMemberBean();
			JsonUtil.mapToBean(map, cm);
			PartyServiceFactory.getPartySV().saveCircleMemberInfo(cm);
	        HttpJsonUtil.showInfo(response,"保存成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	//踢出圈子
	public void kickoutCircle(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long cId = HttpUtil.getAsLong(request, "cId");
			String users = HttpUtil.getAsString(request, "userIds");
			StringTokenizer toKenizer = new StringTokenizer(users, ",");
			long[] userIds = new long[toKenizer.countTokens()];
			int i = 0;        
			while (toKenizer.hasMoreElements()) {           
				userIds[i++] = Long.valueOf(toKenizer.nextToken());        
			}
			PartyServiceFactory.getPartySV().kickoutCircle(userIds, cId);
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	private void remoteImg(long cId,String mediaId)throws Exception{
		//从微信服务器下载
		if(log.isDebugEnabled())
			log.debug("mediaId:"+mediaId);
		InputStream is = WechatUtils.httpRequest(WechatCommons.getUrlMediaGet(mediaId), WechatCommons.HttpGet, null);
		byte[] datas = CommonUtil.readBytes(is);
		//上传到七牛
		RemoteResouseManager.upload(datas, "c_"+cId);
	}
	
}
