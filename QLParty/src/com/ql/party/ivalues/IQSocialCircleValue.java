package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IQSocialCircleValue extends DataStructInterface,ISocialCircleValue{

  public final static  String S_Job = "Job";
  public final static  String S_Phone = "Phone";
  public final static  String S_Username = "UserName";
  public final static  String S_City = "City";
  public final static  String S_Userid = "UserId";


public String getJob();

public String getPhone();

public String getUsername();

public String getCity();

public long getUserid();

public  void setJob(String value);

public  void setPhone(String value);

public  void setUsername(String value);

public  void setCity(String value);

public void setUserid(long value);

}
