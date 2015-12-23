package com.ql.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class QLCentralControlServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	public static final String PLUG_INS_KEY = "PLUG_INS_KEY";
	private static transient Log log = LogFactory.getLog(QLCentralControlServlet.class);
	private static QLRequestProcessor rp = new QLRequestProcessor();
	
	public void init() throws ServletException {
		super.init();
	}

	public void destroy() {
		super.destroy();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try {
			rp.process(req, resp);
		} catch (Exception ex) {
			log.error(ex);
			ex.printStackTrace();
			throw new ServletException(ex);
		}
	}

	protected void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		doGet(arg0, arg1);
	}
}
