package com.ql.album.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.album.ivalues.*;

public class AlbumLabelBean extends DataContainer implements DataContainerInterface,IAlbumLabelValue{

  private static String  m_boName = "com.ql.album.bo.AlbumLabel";



  public final static  String S_Labelid = "LabelId";
  public final static  String S_State = "State";
  public final static  String S_Labelname = "LabelName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Groupid = "GroupId";
  public final static  String S_Donedate = "DoneDate";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public AlbumLabelBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initLabelid(long value){
     this.initProperty(S_Labelid,new Long(value));
  }
  public  void setLabelid(long value){
     this.set(S_Labelid,new Long(value));
  }
  public  void setLabelidNull(){
     this.set(S_Labelid,null);
  }

  public long getLabelid(){
        return DataType.getAsLong(this.get(S_Labelid));
  
  }
  public long getLabelidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Labelid));
      }

  public void initState(long value){
     this.initProperty(S_State,new Long(value));
  }
  public  void setState(long value){
     this.set(S_State,new Long(value));
  }
  public  void setStateNull(){
     this.set(S_State,null);
  }

  public long getState(){
        return DataType.getAsLong(this.get(S_State));
  
  }
  public long getStateInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_State));
      }

  public void initLabelname(String value){
     this.initProperty(S_Labelname,value);
  }
  public  void setLabelname(String value){
     this.set(S_Labelname,value);
  }
  public  void setLabelnameNull(){
     this.set(S_Labelname,null);
  }

  public String getLabelname(){
       return DataType.getAsString(this.get(S_Labelname));
  
  }
  public String getLabelnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Labelname));
      }

  public void initUserid(long value){
     this.initProperty(S_Userid,new Long(value));
  }
  public  void setUserid(long value){
     this.set(S_Userid,new Long(value));
  }
  public  void setUseridNull(){
     this.set(S_Userid,null);
  }

  public long getUserid(){
        return DataType.getAsLong(this.get(S_Userid));
  
  }
  public long getUseridInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Userid));
      }

  public void initGroupid(long value){
     this.initProperty(S_Groupid,new Long(value));
  }
  public  void setGroupid(long value){
     this.set(S_Groupid,new Long(value));
  }
  public  void setGroupidNull(){
     this.set(S_Groupid,null);
  }

  public long getGroupid(){
        return DataType.getAsLong(this.get(S_Groupid));
  
  }
  public long getGroupidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Groupid));
      }

  public void initDonedate(Timestamp value){
     this.initProperty(S_Donedate,value);
  }
  public  void setDonedate(Timestamp value){
     this.set(S_Donedate,value);
  }
  public  void setDonedateNull(){
     this.set(S_Donedate,null);
  }

  public Timestamp getDonedate(){
        return DataType.getAsDateTime(this.get(S_Donedate));
  
  }
  public Timestamp getDonedateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_Donedate));
      }


 
 }

