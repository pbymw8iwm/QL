package com.ql.party.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ql.wechat.IWechatOp;
import com.ql.wechat.ReceiveXmlEntity;

public class WechatOpImpl implements IWechatOp {

	/**
	 * �������¼�
	 * @param xmlEntity
	 * @return
	 */
	public String processSubscribe(ReceiveXmlEntity xmlEntity){
		return "��ӭʹ�þۻ����֣�����ʹ�ÿɲ鿴����";
	}

	/**
	 * �����˶��¼�
	 * @param xmlEntity
	 * @return
	 */
	public String processUnsubscribe(ReceiveXmlEntity xmlEntity){
		return "��л����ʹ�ã��ڴ������ٴε�����";
	}
	
	/**
	 * �����ά��ɨ��
	 * @param xmlEntity
	 * @return
	 */
	public String processScan(ReceiveXmlEntity xmlEntity){
		return "��ӭʹ�þۻ����֣�";
	}
	
	/**
	 * ������ͨ��Ϣ
	 * @param xmlEntity
	 * @return
	 */
	public String processMsg(ReceiveXmlEntity xmlEntity){
		return "��ӭʹ�þۻ����֣�";
	}

	/**
	 * �������Ȩ�Ĳ˵�����
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	public String getOpUrl(HttpServletRequest request, HttpServletResponse response, String param){
		return null;
	}
}
