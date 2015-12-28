package com.ql.party.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	
	private void remoteImg(long cId,String mediaId)throws Exception{
		//从微信服务器下载
		InputStream is = WechatUtils.httpRequest(WechatCommons.getUrlMediaGet(mediaId), WechatCommons.HttpGet, null);
		byte[] datas = readBytes(is);
		//上传到七牛
		RemoteResouseManager.upload(datas, "c_"+cId+".jpg");
	}
	
	public static byte[] readBytes(InputStream in) throws IOException {
		BufferedInputStream bufin = new BufferedInputStream(in);
		int buffSize = 1024;
		ByteArrayOutputStream out = new ByteArrayOutputStream(buffSize);
		byte[] temp = new byte[buffSize];
		int size = 0;
		while ((size = bufin.read(temp)) != -1) {
			out.write(temp, 0, size);
		}
		bufin.close();
		byte[] content = out.toByteArray();
		return content;
	}
}
