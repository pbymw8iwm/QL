package com.ql.party.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ql.wechat.IWechatOp;
import com.ql.wechat.ReceiveXmlEntity;

public class WechatOpImpl implements IWechatOp {

	/**
	 * 处理订阅事件
	 * @param xmlEntity
	 * @return
	 */
	public String processSubscribe(ReceiveXmlEntity xmlEntity){
		return "欢迎使用聚会助手！初次使用可查看帮助";
	}

	/**
	 * 处理退订事件
	 * @param xmlEntity
	 * @return
	 */
	public String processUnsubscribe(ReceiveXmlEntity xmlEntity){
		return "感谢您的使用！期待您的再次到来！";
	}
	
	/**
	 * 处理二维码扫描
	 * @param xmlEntity
	 * @return
	 */
	public String processScan(ReceiveXmlEntity xmlEntity){
		return "欢迎使用聚会助手！";
	}
	
	/**
	 * 处理普通消息
	 * @param xmlEntity
	 * @return
	 */
	public String processMsg(ReceiveXmlEntity xmlEntity){
		return "欢迎使用聚会助手！";
	}

	/**
	 * 处理带授权的菜单链接
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	public String getOpUrl(HttpServletRequest request, HttpServletResponse response, String param){
		return null;
	}
}
