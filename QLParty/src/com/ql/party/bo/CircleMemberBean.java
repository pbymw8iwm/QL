package com.ql.party.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.party.ivalues.*;

public class CircleMemberBean extends DataContainer implements DataContainerInterface,ICircleMemberValue{

  private static String  m_boName = "com.ql.party.bo.CircleMember";



  public final static  String S_Job = "Job";
  public final static  String S_Phone = "Phone";
  public final static  String S_State = "State";
  public final static  String S_Username = "UserName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Cid = "CId";
  public final static  String S_City = "City";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public CircleMemberBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initJob(String value){
     this.initProperty(S_Job,value);
  }
  public  void setJob(String value){
     this.set(S_Job,value);
  }
  public  void setJobNull(){
     this.set(S_Job,null);
  }

  public String getJob(){
       return DataType.getAsString(this.get(S_Job));
  
  }
  public String getJobInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Job));
      }

  public void initPhone(String value){
     this.initProperty(S_Phone,value);
  }
  public  void setPhone(String value){
     this.set(S_Phone,value);
  }
  public  void setPhoneNull(){
     this.set(S_Phone,null);
  }

  public String getPhone(){
       return DataType.getAsString(this.get(S_Phone));
  
  }
  public String getPhoneInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Phone));
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

  public void initUsername(String value){
     this.initProperty(S_Username,value);
  }
  public  void setUsername(String value){
     this.set(S_Username,value);
  }
  public  void setUsernameNull(){
     this.set(S_Username,null);
  }

  public String getUsername(){
       return DataType.getAsString(this.get(S_Username));
  
  }
  public String getUsernameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Username));
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


 
 }

