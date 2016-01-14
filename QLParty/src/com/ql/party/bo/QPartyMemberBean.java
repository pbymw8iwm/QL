package com.ql.party.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.party.ivalues.*;

public class QPartyMemberBean extends DataContainer implements DataContainerInterface,IQPartyMemberValue{

  private static String  m_boName = "com.ql.party.bo.QPartyMember";



  public final static  String S_State = "State";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Username = "UserName";
  public final static  String S_Userid = "UserId";
  public final static  String S_Pcount = "PCount";
  public final static  String S_Cid = "CId";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public QPartyMemberBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initState(int value){
     this.initProperty(S_State,new Integer(value));
  }
  public  void setState(int value){
     this.set(S_State,new Integer(value));
  }
  public  void setStateNull(){
     this.set(S_State,null);
  }

  public int getState(){
        return DataType.getAsInt(this.get(S_State));
  
  }
  public int getStateInitialValue(){
        return DataType.getAsInt(this.getOldObj(S_State));
      }

  public void initPartyid(long value){
     this.initProperty(S_Partyid,new Long(value));
  }
  public  void setPartyid(long value){
     this.set(S_Partyid,new Long(value));
  }
  public  void setPartyidNull(){
     this.set(S_Partyid,null);
  }

  public long getPartyid(){
        return DataType.getAsLong(this.get(S_Partyid));
  
  }
  public long getPartyidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Partyid));
      }

  public void initRemarks(String value){
     this.initProperty(S_Remarks,value);
  }
  public  void setRemarks(String value){
     this.set(S_Remarks,value);
  }
  public  void setRemarksNull(){
     this.set(S_Remarks,null);
  }

  public String getRemarks(){
       return DataType.getAsString(this.get(S_Remarks));
  
  }
  public String getRemarksInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Remarks));
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

  public void initPcount(long value){
     this.initProperty(S_Pcount,new Long(value));
  }
  public  void setPcount(long value){
     this.set(S_Pcount,new Long(value));
  }
  public  void setPcountNull(){
     this.set(S_Pcount,null);
  }

  public long getPcount(){
        return DataType.getAsLong(this.get(S_Pcount));
  
  }
  public long getPcountInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Pcount));
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


 
 }

