package com.ql.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ql.ivalues.IWechatUserValue;

/**
 * 处理微信传入的消息
 * @author linhl
 *
 */
public interface IWechatOp {

	/**
	 * 处理订阅事件
	 * @param xmlEntity
	 * @return
	 */
	public String processSubscribe(ReceiveXmlEntity xmlEntity);

	/**
	 * 处理退订事件
	 * @param xmlEntity
	 * @return
	 */
	public String processUnsubscribe(ReceiveXmlEntity xmlEntity);
	
	/**
	 * 处理带参二维码扫描
	 * @param xmlEntity
	 * @return
	 */
	public String processScan(ReceiveXmlEntity xmlEntity);
	/**
	 * 处理普通消息
	 * @param xmlEntity
	 * @return
	 */
	public String processMsg(ReceiveXmlEntity xmlEntity);
	
	/**
	 * 处理带授权的菜单链接
	 * @param param
	 * @param wechatUser
	 * @return
	 */
	public String getOpUrl(String param, IWechatUserValue wechatUser);
	
	/**
	 * 处理未知用户的带授权菜单链接
	 * @param param
	 * @return
	 */
	public String getNoUserOpUrl(String param);
}
