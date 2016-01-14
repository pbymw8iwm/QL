package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IPartyMsgValue extends DataStructInterface{

  public final static  String S_Msgid = "MsgId";
  public final static  String S_State = "State";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Message = "Message";
  public final static  String S_Userid = "UserId";
  public final static  String S_Cid = "CId";
  public final static  String S_Donedate = "DoneDate";


public long getMsgid();

public long getState();

public long getPartyid();

public String getMessage();

public long getUserid();

public long getCid();

public Timestamp getDonedate();


public  void setMsgid(long value);

public  void setState(long value);

public  void setPartyid(long value);

public  void setMessage(String value);

public  void setUserid(long value);

public  void setCid(long value);

public  void setDonedate(Timestamp value);
}
