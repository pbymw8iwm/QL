package com.ql.album.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IAlbumLabelValue extends DataStructInterface{

  public final static  String S_Labelid = "LabelId";
  public final static  String S_State = "State";
  public final static  String S_Labelname = "LabelName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Groupid = "GroupId";
  public final static  String S_Donedate = "DoneDate";


public long getLabelid();

public long getState();

public String getLabelname();

public long getUserid();

public long getGroupid();

public Timestamp getDonedate();


public  void setLabelid(long value);

public  void setState(long value);

public  void setLabelname(String value);

public  void setUserid(long value);

public  void setGroupid(long value);

public  void setDonedate(Timestamp value);
}
