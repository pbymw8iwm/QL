package com.ql.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IWechatUserValue extends DataStructInterface{

  public final static  String S_Usertype = "UserType";
  public final static  String S_State = "State";
  public final static  String S_Userid = "UserId";
  public final static  String S_Openid = "OpenId";


public long getUsertype();

public long getState();

public long getUserid();

public String getOpenid();


public  void setUsertype(long value);

public  void setState(long value);

public  void setUserid(long value);

public  void setOpenid(String value);
}
