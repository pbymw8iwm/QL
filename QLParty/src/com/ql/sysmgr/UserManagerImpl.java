package com.ql.sysmgr;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ai.appframe2.complex.cache.CacheFactory;
import com.ai.appframe2.privilege.UserInfoInterface;
import com.ai.appframe2.web.HttpUtil;
import com.ai.frame.loginmgr.AbstractUserManagerImpl;
import com.ql.cache.WechatUserCacheImpl;
import com.ql.ivalues.IWechatUserValue;
import com.ql.sysmgr.UserInfoImpl;

public class UserManagerImpl extends AbstractUserManagerImpl{
	private static final Log log = LogFactory.getLog(UserManagerImpl.class);
			
	public UserInfoInterface getBlankUserInfo() {
		return new UserInfoImpl();
	}
	
	public UserInfoInterface loginIn(String pUserCode, String pPassWord, long domainId, int curLoginNum, HttpServletRequest req) throws Exception {
		long loginType = HttpUtil.getAsLong(req, "LoginType");
		long loginId = HttpUtil.getAsLong(req, "LoginId");

		UserInfoInterface user = getBlankUserInfo();
		user.setID(loginId);
		user.set("type", loginType);
		
		IWechatUserValue wechatUser = (IWechatUserValue)CacheFactory.get(WechatUserCacheImpl.class, loginType+"_"+loginId);
		if(wechatUser != null){
			user.setName(wechatUser.getName());
			user.setCode(wechatUser.getName());
			user.set("wechatUser", wechatUser);
		}
		return user;
	}
}
