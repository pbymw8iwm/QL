package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface ICircleMemberValue extends DataStructInterface{

  public final static  String S_Job = "Job";
  public final static  String S_Phone = "Phone";
  public final static  String S_State = "State";
  public final static  String S_Username = "UserName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Cid = "CId";
  public final static  String S_City = "City";


public String getJob();

public String getPhone();

public long getState();

public String getUsername();

public long getUserid();

public long getCid();

public String getCity();


public  void setJob(String value);

public  void setPhone(String value);

public  void setState(long value);

public  void setUsername(String value);

public  void setUserid(long value);

public  void setCid(long value);

public  void setCity(String value);
}
