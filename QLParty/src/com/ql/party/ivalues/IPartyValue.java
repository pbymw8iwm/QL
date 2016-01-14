package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IPartyValue extends DataStructInterface{

  public final static  String S_Theme = "Theme";
  public final static  String S_Qrticket = "QrTicket";
  public final static  String S_State = "State";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Gatheringplace = "GatheringPlace";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Endtime = "EndTime";
  public final static  String S_Starttime = "StartTime";
  public final static  String S_Creater = "Creater";
  public final static  String S_Qrdate = "QrDate";
  public final static  String S_Cid = "CId";
  public final static  String S_Donedate = "DoneDate";


public String getTheme();

public String getQrticket();

public long getState();

public long getPartyid();

public String getGatheringplace();

public String getRemarks();

public Timestamp getEndtime();

public Timestamp getStarttime();

public long getCreater();

public Timestamp getQrdate();

public long getCid();

public Timestamp getDonedate();


public  void setTheme(String value);

public  void setQrticket(String value);

public  void setState(long value);

public  void setPartyid(long value);

public  void setGatheringplace(String value);

public  void setRemarks(String value);

public  void setEndtime(Timestamp value);

public  void setStarttime(Timestamp value);

public  void setCreater(long value);

public  void setQrdate(Timestamp value);

public  void setCid(long value);

public  void setDonedate(Timestamp value);
}
