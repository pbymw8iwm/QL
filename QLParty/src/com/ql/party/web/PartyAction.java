package com.ql.party.web;

import java.io.InputStream;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.common.SessionManager;
import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.web.HttpUtil;
import com.ql.action.QLBaseAction;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.common.CommonUtil;
import com.ql.common.HttpJsonUtil;
import com.ql.common.JsonUtil;
import com.ql.ivalues.ICfStaticDataValue;
import com.ql.ivalues.IWechatUserValue;
import com.ql.party.bo.CircleMemberBean;
import com.ql.party.bo.PartyBean;
import com.ql.party.bo.SocialCircleBean;
import com.ql.party.ivalues.ICircleMemberValue;
import com.ql.party.ivalues.IPartyPhotoValue;
import com.ql.party.ivalues.IQPartyMemberValue;
import com.ql.party.ivalues.IQPartyValue;
import com.ql.party.ivalues.IQSocialCircleValue;
import com.ql.party.ivalues.ISocialCircleValue;
import com.ql.party.service.PartyServiceFactory;
import com.ql.party.sysmgr.RemoteResouseManager;
import com.ql.party.wechat.WechatOpImpl;
import com.ql.sysmgr.QLServiceFactory;
import com.ql.wechat.MsgForwardTool;
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
	 * 查询圈子
	 * @param cId
	 * @return
	 * @throws Exception
	 */
	public static IQSocialCircleValue getSocialCircleByUser(long cId,boolean isExtInfo)throws Exception{
		return PartyServiceFactory.getPartySV().getSocialCircle(cId, SessionManager.getUser().getID(), isExtInfo);
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

	/**
	 * 查询聚会
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public static IQPartyValue getParty(long partyId,boolean isExtInfo)throws Exception{
		return PartyServiceFactory.getPartySV().getParty(partyId, isExtInfo);
	}
	
	/**
	 * 查询聚会的圈友
	 * @param partyId
	 * @return
	 * @throws Exception
	 */
	public static IQPartyMemberValue[] getPartyMembers(long partyId)throws Exception{
		return PartyServiceFactory.getPartySV().getPartyMembers(partyId);
	}
	
	/**
	 * 查询聚会
	 * @param cId 0：查询未结束的聚会，非0：查询此圈的聚会
	 * @return
	 * @throws Exception
	 */
	public static IQPartyValue[] getPartys(long cId)throws Exception{
		if(cId == 0)
			return PartyServiceFactory.getPartySV().getPartys(SessionManager.getUser().getID());
		return PartyServiceFactory.getPartySV().getPartys(cId, SessionManager.getUser().getID());
	}
	
	public static IPartyPhotoValue[] getPhotoes(long partyId)throws Exception{
		return PartyServiceFactory.getPartySV().getPhotoes(partyId);
	}

	/*************************************************
	 * action
	 *************************************************/
	
	public void getCircleShareLink(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long cId = HttpUtil.getAsLong(request, "cId");
			String link = WechatCommons.getUrlView(WechatOpImpl.Type_JoinCircle+cId);
	        HttpJsonUtil.showInfo(response,link);
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	public void getPartyShareLink(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long pId = HttpUtil.getAsLong(request, "pId");
			String link = WechatCommons.getUrlView(WechatOpImpl.Type_JoinParty+pId);
	        HttpJsonUtil.showInfo(response,link);
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
		
		/********************************************
		 * 圈子
		 ********************************************/
	
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
			remoteCircleImg(cId,mediaId);	
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
			remoteCircleImg(cId,mediaId);			
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
			String cName = HttpUtil.getAsString(request, "cName");
			long userId = HttpUtil.getAsLong(request, "userId");
			PartyServiceFactory.getPartySV().changeMaster(cId, userId);
			
			try{
				IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, "0_"+userId);
				if(wechatUser != null){
					MsgForwardTool.sendTextMsg(wechatUser.getOpenid(), "您被设置为【"+cName+"】的圈主，\n请点击<a href='"+WechatCommons.getUrlView(WechatOpImpl.Type_CircleInfo+cId)+"'>这里</a>进入");
				}
			}
			catch(Exception ex){
				log.error(userId+"被设置为圈主的消息发送失败："+ex.getMessage());
			}
			
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
			if(StringUtils.isNotBlank(json) && "{}".equals(json) == false){
				Map map = JsonUtil.getMapFromJsObject(json);
				
				CircleMemberBean cm = new CircleMemberBean();
				JsonUtil.mapToBean(map, cm);
				PartyServiceFactory.getPartySV().saveCircleMemberInfo(cm);
			}
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
			String cName = HttpUtil.getAsString(request, "cName");
			String users = HttpUtil.getStringFromBufferedReader(request);
			StringTokenizer toKenizer = new StringTokenizer(users, ",");
			long[] userIds = new long[toKenizer.countTokens()];
			int i = 0;        
			while (toKenizer.hasMoreElements()) {           
				userIds[i++] = Long.valueOf(toKenizer.nextToken());        
			}
			PartyServiceFactory.getPartySV().kickoutCircle(userIds, cId);
			
			//发送消息
			String msg = "您被圈主移出了圈子【"+cName+"】";
			for(long userId : userIds){
				try{
					IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, "0_"+userId);
					if(wechatUser != null){
						MsgForwardTool.sendTextMsg(wechatUser.getOpenid(), msg);
					}
				}
				catch(Exception ex){
					log.error(userId+"的被踢消息发送失败："+ex.getMessage());
				}
			}
			
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	private void remoteCircleImg(long cId,String mediaId)throws Exception{
		//从微信服务器下载
		if(log.isDebugEnabled())
			log.debug("mediaId:"+mediaId);
		InputStream is = WechatUtils.httpRequest(WechatCommons.getUrlMediaGet(mediaId), WechatCommons.HttpGet, null);
		byte[] datas = CommonUtil.readBytes(is);
		//上传到七牛
		RemoteResouseManager.upload(datas, "c_"+cId);
	}

	
		/********************************************
		 * 聚会
		 ********************************************/

	//创建聚会
	public void createParty(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String json = HttpUtil.getStringFromBufferedReader(request);
			Map map = JsonUtil.getMapFromJsObject(json);
			
			PartyBean party = new PartyBean();
			JsonUtil.mapToBean(map, party);
			party.setCreater(SessionManager.getUser().getID());
			long partyId = PartyServiceFactory.getPartySV().saveParty(party);
	        HttpJsonUtil.showInfo(response,partyId+"");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}	
	
	//修改聚会信息
	public void saveParty(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String json = HttpUtil.getStringFromBufferedReader(request);
			Map map = JsonUtil.getMapFromJsObject(json);
			
			PartyBean party = new PartyBean();
			JsonUtil.mapToBean(map, party);
			long partyId = PartyServiceFactory.getPartySV().saveParty(party);
	        HttpJsonUtil.showInfo(response,partyId+"");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	//设置参与信息
	public void setPartyMember(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			long partyId = HttpUtil.getAsLong(request, "partyId");
			long state = HttpUtil.getAsLong(request, "state");
			long count = HttpUtil.getAsLong(request, "count");
			
			PartyServiceFactory.getPartySV().setPartyMember(partyId, SessionManager.getUser().getID(), state, count);
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	public void addPartyPhoto(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String mediaIds = HttpUtil.getStringFromBufferedReader(request);
			long partyId = HttpUtil.getAsLong(request, "partyId");
			long cId = HttpUtil.getAsLong(request, "cId");
			long userId = SessionManager.getUser().getID();
			PartyServiceFactory.getPartySV().addPhotoes(partyId, cId, userId, mediaIds.split(","));
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
	public void delPartyPhoto(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String ids = HttpUtil.getStringFromBufferedReader(request);
			String[] sPIds = ids.split(";")[0].split(",");
			String[] sUIds = ids.split(";")[1].split(",");
			long[] photoIds = new long[sPIds.length];
			long[] userIds = new long[sPIds.length];
			for(int i=0;i<photoIds.length;i++){
				photoIds[i] = Long.parseLong(sPIds[i]);
				userIds[i] = Long.parseLong(sUIds[i]);
			}
			
			long partyId = HttpUtil.getAsLong(request, "partyId");
			long cId = HttpUtil.getAsLong(request, "cId");
			PartyServiceFactory.getPartySV().delPhotos(partyId, cId, photoIds, userIds);
	        HttpJsonUtil.showInfo(response,"处理成功!");
	    }
	    catch(Exception ex){
	      log.error(ex.getMessage(),ex);
	      HttpJsonUtil.showError(response,ex.getMessage());
	    }
	}
	
}
