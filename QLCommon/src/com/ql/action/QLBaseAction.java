package com.ql.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QLBaseAction {

	private static transient Log log = LogFactory.getLog(QLBaseAction.class);
	public static final String GBK_XML_CONTENT_TYPE = "text/xml; charset=GBK";
	public static final String GBK_HTML_CONTENT_TYPE = "text/html; charset=GBK";
	
	public String getDefaultAction() {
		return "";
	}

	public String getErrorAction() {
		return "";
	}

	public boolean isCheckUserValid() {
		throw new RuntimeException("is depreateded");
	}

	/**
	 * @deprecated Method setCheckUserValid is deprecated
	 */

	public void setCheckUserValid(boolean flag1) {
	}

	/**
	 * @deprecated Method writeResponseXml is deprecated
	 */

	protected void writeResponseXml(HttpServletResponse response, Object object)
			throws Exception {
		writeResponse(response, object, "text/xml; charset=GBK");
	}

	/**
	 * @deprecated Method writeResponseHtml is deprecated
	 */

	protected void writeResponseHtml(HttpServletResponse response, Object object)
			throws Exception {
		writeResponse(response, object, "text/html; charset=GBK");
	}

	/**
	 * @deprecated Method writeResponse is deprecated
	 */

	protected void writeResponse(HttpServletResponse response, Object object,
			String contentType) throws Exception {
		response.setContentType(contentType.trim());
		response.getWriter().print(object);
	}

	/**
	 * @deprecated Method getRequestForString is deprecated
	 */

	protected String getRequestForString(HttpServletRequest request, String name) {
		String rtn = null;
		try {
			rtn = (String) request.getAttribute(name);
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
		return rtn;
	}

	/**
	 * @deprecated Method getRequestForLong is deprecated
	 */

	protected long getRequestForLong(HttpServletRequest request, String name) {
		long rtn = 0L;
		try {
			rtn = Long.parseLong((String) request.getAttribute(name));
		} catch (Exception ex) {
			log.error(ex.getMessage(),ex);
		}
		return rtn;
	}

	/**
	 * @deprecated Method getRequestForDate is deprecated
	 */

	protected Date getRequestForDate(HttpServletRequest request, String name,
			String pattern) {
		Date date = null;
		try {
			DateFormat dateformat = new SimpleDateFormat(pattern);
			date = dateformat.parse((String) request.getAttribute(name));
		} catch (ParseException ex) {
			log.error(ex.getMessage(),ex);
		}
		return date;
	}
}
