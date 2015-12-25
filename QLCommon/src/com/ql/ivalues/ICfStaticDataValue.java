package com.ql.ivalues;
import com.ai.appframe2.common.DataStructInterface;
import java.sql.Timestamp;
public interface ICfStaticDataValue extends DataStructInterface{

  public final static  String S_Codevalue = "CodeValue";
  public final static  String S_State = "State";
  public final static  String S_Sortid = "SortId";
  public final static  String S_Codetype = "CodeType";
  public final static  String S_Codename = "CodeName";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Extvalue = "ExtValue";


public String getCodevalue();

public long getState();

public long getSortid();

public String getCodetype();

public String getCodename();

public String getRemarks();

public String getExtvalue();


public  void setCodevalue(String value);

public  void setState(long value);

public  void setSortid(long value);

public  void setCodetype(String value);

public  void setCodename(String value);

public  void setRemarks(String value);

public  void setExtvalue(String value);
}
