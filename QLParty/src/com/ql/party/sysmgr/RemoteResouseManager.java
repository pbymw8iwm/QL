package com.ql.party.sysmgr;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.ql.wechat.WechatCommons;

public class RemoteResouseManager {
	
	private static final Log log = LogFactory.getLog(RemoteResouseManager.class);

	public static String Domain = null;
	private static String Bucket = null;
	private static Auth S_Auth = null;
	private static UploadManager S_UploadManager = new UploadManager();
	
	static{
		try{
			Properties pps = new Properties();
			InputStream in = WechatCommons.class.getClassLoader().getResourceAsStream("remote");
			pps.load(in);
			Domain = pps.getProperty("Domain");
			Bucket = pps.getProperty("Bucket");
			String AK = pps.getProperty("AK");
			String SK = pps.getProperty("SK");
			S_Auth = Auth.create(AK, SK);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	// 
	private static String getUpToken(String key){
	    return S_Auth.uploadToken(Bucket, key);
	}
	
	//上传内存中数据，普通上传
	public static String upload(byte[] data)throws Exception{
		return upload(data,null);
	}
	
	//上传内存中数据，覆盖上传
	public static String upload(byte[] data, String key)throws Exception{
	  try {
	        Response res = S_UploadManager.put(data, key, getUpToken(key));
        	DefaultPutRet ret = res.jsonToObject(DefaultPutRet.class);
        	return ret.key;
	    } catch (QiniuException e) {
	        Response r = e.response;
	        // 请求失败时简单状态信息
	        log.error(r.toString());
	        try {
	            // 响应的文本信息
	            log.error(r.bodyString());
	        } catch (QiniuException e1) {
	            //ignore
	        }
	        throw e;
	    }
	}

}
