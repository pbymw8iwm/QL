package com.ql.party.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.party.ivalues.*;

public class SocialCircleBean extends DataContainer implements DataContainerInterface,ISocialCircleValue{

  private static String  m_boName = "com.ql.party.bo.SocialCircle";



  public final static  String S_Cname = "CName";
  public final static  String S_State = "State";
  public final static  String S_Ctype = "CType";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Creater = "Creater";
  public final static  String S_Createdate = "CreateDate";
  public final static  String S_Cid = "CId";
  public final static  String S_Donedate = "DoneDate";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public SocialCircleBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCname(String value){
     this.initProperty(S_Cname,value);
  }
  public  void setCname(String value){
     this.set(S_Cname,value);
  }
  public  void setCnameNull(){
     this.set(S_Cname,null);
  }

  public String getCname(){
       return DataType.getAsString(this.get(S_Cname));
  
  }
  public String getCnameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Cname));
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

  public void initCtype(long value){
     this.initProperty(S_Ctype,new Long(value));
  }
  public  void setCtype(long value){
     this.set(S_Ctype,new Long(value));
  }
  public  void setCtypeNull(){
     this.set(S_Ctype,null);
  }

  public long getCtype(){
        return DataType.getAsLong(this.get(S_Ctype));
  
  }
  public long getCtypeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Ctype));
      }

  public void initImagedata(String value){
     this.initProperty(S_Imagedata,value);
  }
  public  void setImagedata(String value){
     this.set(S_Imagedata,value);
  }
  public  void setImagedataNull(){
     this.set(S_Imagedata,null);
  }

  public String getImagedata(){
       return DataType.getAsString(this.get(S_Imagedata));
  
  }
  public String getImagedataInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Imagedata));
      }

  public void initCreater(long value){
     this.initProperty(S_Creater,new Long(value));
  }
  public  void setCreater(long value){
     this.set(S_Creater,new Long(value));
  }
  public  void setCreaterNull(){
     this.set(S_Creater,null);
  }

  public long getCreater(){
        return DataType.getAsLong(this.get(S_Creater));
  
  }
  public long getCreaterInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Creater));
      }

  public void initCreatedate(Timestamp value){
     this.initProperty(S_Createdate,value);
  }
  public  void setCreatedate(Timestamp value){
     this.set(S_Createdate,value);
  }
  public  void setCreatedateNull(){
     this.set(S_Createdate,null);
  }

  public Timestamp getCreatedate(){
        return DataType.getAsDateTime(this.get(S_Createdate));
  
  }
  public Timestamp getCreatedateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_Createdate));
      }

  public void initCid(long value){
     this.initProperty(S_Cid,new Long(value));
  }
  public  void setCid(long value){
     this.set(S_Cid,new Long(value));
  }
  public  void setCidNull(){
     this.set(S_Cid,null);
  }

  public long getCid(){
        return DataType.getAsLong(this.get(S_Cid));
  
  }
  public long getCidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Cid));
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

