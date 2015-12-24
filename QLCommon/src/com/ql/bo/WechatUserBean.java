package com.ql.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.ivalues.*;

public class WechatUserBean extends DataContainer implements DataContainerInterface,IWechatUserValue{

  private static String  m_boName = "com.ql.bo.WechatUser";



  public final static  String S_Usertype = "UserType";
  public final static  String S_Name = "Name";
  public final static  String S_State = "State";
  public final static  String S_Gender = "Gender";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Userid = "UserId";
  public final static  String S_Openid = "OpenId";
  public final static  String S_Createdate = "CreateDate";
  public final static  String S_City = "City";
  public final static  String S_Donedate = "DoneDate";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public WechatUserBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initUsertype(long value){
     this.initProperty(S_Usertype,new Long(value));
  }
  public  void setUsertype(long value){
     this.set(S_Usertype,new Long(value));
  }
  public  void setUsertypeNull(){
     this.set(S_Usertype,null);
  }

  public long getUsertype(){
        return DataType.getAsLong(this.get(S_Usertype));
  
  }
  public long getUsertypeInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Usertype));
      }

  public void initName(String value){
     this.initProperty(S_Name,value);
  }
  public  void setName(String value){
     this.set(S_Name,value);
  }
  public  void setNameNull(){
     this.set(S_Name,null);
  }

  public String getName(){
       return DataType.getAsString(this.get(S_Name));
  
  }
  public String getNameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Name));
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

  public void initGender(long value){
     this.initProperty(S_Gender,new Long(value));
  }
  public  void setGender(long value){
     this.set(S_Gender,new Long(value));
  }
  public  void setGenderNull(){
     this.set(S_Gender,null);
  }

  public long getGender(){
        return DataType.getAsLong(this.get(S_Gender));
  
  }
  public long getGenderInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Gender));
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

  public void initOpenid(String value){
     this.initProperty(S_Openid,value);
  }
  public  void setOpenid(String value){
     this.set(S_Openid,value);
  }
  public  void setOpenidNull(){
     this.set(S_Openid,null);
  }

  public String getOpenid(){
       return DataType.getAsString(this.get(S_Openid));
  
  }
  public String getOpenidInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Openid));
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

  public void initCity(String value){
     this.initProperty(S_City,value);
  }
  public  void setCity(String value){
     this.set(S_City,value);
  }
  public  void setCityNull(){
     this.set(S_City,null);
  }

  public String getCity(){
       return DataType.getAsString(this.get(S_City));
  
  }
  public String getCityInitialValue(){
        return DataType.getAsString(this.getOldObj(S_City));
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

