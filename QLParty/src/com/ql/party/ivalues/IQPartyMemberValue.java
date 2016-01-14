package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IQPartyMemberValue extends DataStructInterface{

  public final static  String S_State = "State";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Username = "UserName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Pcount = "PCount";
  public final static  String S_Cid = "CId";


public int getState();

public long getPartyid();

public String getRemarks();

public String getUsername();

public long getUserid();

public long getPcount();

public long getCid();


public  void setState(int value);

public  void setPartyid(long value);

public  void setRemarks(String value);

public  void setUsername(String value);

public  void setUserid(long value);

public  void setPcount(long value);

public  void setCid(long value);
}
