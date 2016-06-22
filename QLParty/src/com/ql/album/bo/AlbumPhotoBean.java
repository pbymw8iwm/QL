package com.ql.album.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.album.ivalues.*;

public class AlbumPhotoBean extends DataContainer implements DataContainerInterface,IAlbumPhotoValue{

  private static String  m_boName = "com.ql.album.bo.AlbumPhoto";



  public final static  String S_Photodata = "PhotoData";
  public final static  String S_State = "State";
  public final static  String S_Labels = "Labels";
  public final static  String S_Userid = "UserId";
  public final static  String S_Photoid = "PhotoId";
  public final static  String S_Donedate = "DoneDate";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public AlbumPhotoBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initPhotodata(String value){
     this.initProperty(S_Photodata,value);
  }
  public  void setPhotodata(String value){
     this.set(S_Photodata,value);
  }
  public  void setPhotodataNull(){
     this.set(S_Photodata,null);
  }

  public String getPhotodata(){
       return DataType.getAsString(this.get(S_Photodata));
  
  }
  public String getPhotodataInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Photodata));
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

  public void initLabels(String value){
     this.initProperty(S_Labels,value);
  }
  public  void setLabels(String value){
     this.set(S_Labels,value);
  }
  public  void setLabelsNull(){
     this.set(S_Labels,null);
  }

  public String getLabels(){
       return DataType.getAsString(this.get(S_Labels));
  
  }
  public String getLabelsInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Labels));
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

  public void initPhotoid(long value){
     this.initProperty(S_Photoid,new Long(value));
  }
  public  void setPhotoid(long value){
     this.set(S_Photoid,new Long(value));
  }
  public  void setPhotoidNull(){
     this.set(S_Photoid,null);
  }

  public long getPhotoid(){
        return DataType.getAsLong(this.get(S_Photoid));
  
  }
  public long getPhotoidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Photoid));
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

