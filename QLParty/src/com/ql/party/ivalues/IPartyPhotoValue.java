package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IPartyPhotoValue extends DataStructInterface{

  public final static  String S_Photodata = "PhotoData";
  public final static  String S_State = "State";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Userid = "UserId";
  public final static  String S_Photoid = "PhotoId";
  public final static  String S_Cid = "CId";
  public final static  String S_Donedate = "DoneDate";


public String getPhotodata();

public long getState();

public long getPartyid();

public long getUserid();

public long getPhotoid();

public long getCid();

public Timestamp getDonedate();


public  void setPhotodata(String value);

public  void setState(long value);

public  void setPartyid(long value);

public  void setUserid(long value);

public  void setPhotoid(long value);

public  void setCid(long value);

public  void setDonedate(Timestamp value);
}
