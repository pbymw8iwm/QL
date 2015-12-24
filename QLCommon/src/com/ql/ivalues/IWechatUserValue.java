package com.ql.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IWechatUserValue extends DataStructInterface{

  public final static  String S_Usertype = "UserType";
  public final static  String S_State = "State";
  public final static  String S_Userid = "UserId";
  public final static  String S_Openid = "OpenId";
  public final static  String S_Name = "Name";
  public final static  String S_Gender = "Gender";
  public final static  String S_City = "City";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Createdate = "CreateDate";
  public final static  String S_Donedate = "DoneDate";


public long getUsertype();

public long getState();

public long getUserid();

public String getOpenid();

public Timestamp getCreatedate();

public String getCity();

public String getName();

public long getGender();

public Timestamp getDonedate();

public String getImagedata();

public  void setUsertype(long value);

public  void setState(long value);

public  void setUserid(long value);

public  void setOpenid(String value);

public  void setImagedata(String value);

public  void setCreatedate(Timestamp value);

public  void setCity(String value);

public  void setName(String value);

public  void setGender(long value);

public  void setDonedate(Timestamp value);
}
