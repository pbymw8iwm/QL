package com.ql.party.sysmgr;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
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
	private static BucketManager S_BucketManager;
	
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
			S_BucketManager = new BucketManager(S_Auth);
		}
		catch(Exception ex){
			log.error(ex.getMessage(),ex);
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
		    if(log.isDebugEnabled())
			    log.debug("上传"+ret.key+":"+res.isOK());
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

	public static void delete(String key)throws Exception{
		S_BucketManager.delete(Bucket, key);
	}
	
	public static void main(String[] args)throws Exception {
		try{
		delete("1_1");
		}
		catch(QiniuException e){
			log.error(e.getMessage(),e);
		}
	}
}
