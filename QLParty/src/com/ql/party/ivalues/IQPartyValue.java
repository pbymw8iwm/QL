package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IQPartyValue extends DataStructInterface{

  public final static  String S_Qrticket = "QrTicket";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Gatheringplace = "GatheringPlace";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Username = "UserName";
  public final static  String S_Creater = "Creater";
  public final static  String S_Cid = "CId";
  public final static  String S_Qrdate = "QrDate";
  public final static  String S_Cname = "CName";
  public final static  String S_Theme = "Theme";
  public final static  String S_State = "State";
  public final static  String S_Endtime = "EndTime";
  public final static  String S_Starttime = "StartTime";
  public final static  String S_Donedate = "DoneDate";


public String getQrticket();

public long getPartyid();

public String getGatheringplace();

public String getImagedata();

public String getRemarks();

public String getUsername();

public long getCreater();

public long getCid();

public Timestamp getQrdate();

public String getCname();

public String getTheme();

public int getState();

public Timestamp getEndtime();

public Timestamp getStarttime();

public Timestamp getDonedate();


public  void setQrticket(String value);

public  void setPartyid(long value);

public  void setGatheringplace(String value);

public  void setImagedata(String value);

public  void setRemarks(String value);

public  void setUsername(String value);

public  void setCreater(long value);

public  void setCid(long value);

public  void setQrdate(Timestamp value);

public  void setCname(String value);

public  void setTheme(String value);

public  void setState(int value);

public  void setEndtime(Timestamp value);

public  void setStarttime(Timestamp value);

public  void setDonedate(Timestamp value);
}
