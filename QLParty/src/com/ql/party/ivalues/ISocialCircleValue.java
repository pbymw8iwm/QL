package com.ql.party.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface ISocialCircleValue extends DataStructInterface{

  public final static  String S_Cname = "CName";
  public final static  String S_State = "State";
  public final static  String S_Ctype = "CType";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Creater = "Creater";
  public final static  String S_Createdate = "CreateDate";
  public final static  String S_Cid = "CId";
  public final static  String S_Donedate = "DoneDate";


public String getCname();

public long getState();

public long getCtype();

public String getImagedata();

public long getCreater();

public Timestamp getCreatedate();

public long getCid();

public Timestamp getDonedate();


public  void setCname(String value);

public  void setState(long value);

public  void setCtype(long value);

public  void setImagedata(String value);

public  void setCreater(long value);

public  void setCreatedate(Timestamp value);

public  void setCid(long value);

public  void setDonedate(Timestamp value);
}
