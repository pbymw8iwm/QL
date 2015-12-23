package com.ql.action;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QLRequestProcessor {

	private static transient Log log = LogFactory.getLog(QLRequestProcessor.class);
	public static final String ACTION = "action";
	protected Class types[] = { HttpServletRequest.class,HttpServletResponse.class };
	
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String methodName = null;
		try {
			QLBaseAction action = getClassInstance(request);
			methodName = getMethodName(action, request);
			if (StringUtils.isBlank(methodName)
					|| StringUtils.isEmpty(methodName)) {
				throw new Exception("方法名为空!");
			}
			exeMethod(action, methodName, request, response);
		} catch (Exception ex) {
			log.error("解析请求的类和方法出错.请检查url中类名和方法名称是否正确. 调用串为"+request.getRequestURI());
			throw ex;
		}
	}

	protected void exeMethod(QLBaseAction action, String pMethodName,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			if (log.isDebugEnabled())
				log.debug("开始处理方法:"+ pMethodName);
			Object args[] = { request, response };
			Method method = action.getClass().getMethod(pMethodName, types);
			
			method.invoke(action, args);
			if (log.isDebugEnabled())
				log.debug("结束处理方法:"+ pMethodName);
		} 
		catch (Exception ex) {
			String errorAction = action.getErrorAction();
			if (errorAction != null && !errorAction.trim().equalsIgnoreCase("")) {
				Object args[] = { request, response };
				Method method = action.getClass().getMethod(errorAction, types);
				method.invoke(action, args);
			} else {
				log.error("实现Action类的方法出现异常",ex);
				throw ex;
			}
		}
	}

	protected QLBaseAction getClassInstance(HttpServletRequest request)
			throws Exception {
		String requestUrl = request.getPathInfo();
		if (StringUtils.isEmpty(requestUrl) || StringUtils.isBlank(requestUrl)) {
			throw new Exception("页面传入参数有误");
		}
		String detailUrl[] = StringUtils.split(requestUrl, '/');
		if (detailUrl.length != 1) {
			throw new Exception("没有找到模块参数，应该符合这个规范:http://localhost:8080/business/so/test?action=save");
		}
		String className = detailUrl[0];
		if (StringUtils.isEmpty(className) || StringUtils.isBlank(className)) {
			throw new Exception("请求参数中没有类名!");
		}
		if (log.isDebugEnabled())
			log.debug("类名为:"+ className);
		if (StringUtils.isBlank(className) || StringUtils.isEmpty(className)) {
			throw new Exception("类名为空!");
		} else {
			Class cls = Class.forName(className);
			QLBaseAction result = (QLBaseAction) cls.getDeclaredConstructor(null).newInstance(null);
			return result;
		}
	}

	protected String getMethodName(QLBaseAction action, HttpServletRequest request)
			throws Exception {
		String methodName = request.getParameter("action");
		if (methodName == null)
			methodName = action.getDefaultAction();
		if (log.isDebugEnabled())
			log.debug("方法名为:"+ methodName);
		if (StringUtils.isEmpty(methodName) || StringUtils.isBlank(methodName)) {
			throw new Exception("请求参数中没有action这个参数或者action对应的这个参数为空");
		} else {
			return methodName;
		}
	}

}
