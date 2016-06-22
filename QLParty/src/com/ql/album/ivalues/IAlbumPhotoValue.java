package com.ql.album.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface IAlbumPhotoValue extends DataStructInterface{

  public final static  String S_Photodata = "PhotoData";
  public final static  String S_State = "State";
  public final static  String S_Labels = "Labels";
  public final static  String S_Userid = "UserId";
  public final static  String S_Photoid = "PhotoId";
  public final static  String S_Donedate = "DoneDate";


public String getPhotodata();

public long getState();

public String getLabels();

public long getUserid();

public long getPhotoid();

public Timestamp getDonedate();


public  void setPhotodata(String value);

public  void setState(long value);

public  void setLabels(String value);

public  void setUserid(long value);

public  void setPhotoid(long value);

public  void setDonedate(Timestamp value);
}
