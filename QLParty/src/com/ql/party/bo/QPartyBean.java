package com.ql.party.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.party.ivalues.*;

public class QPartyBean extends DataContainer implements DataContainerInterface,IQPartyValue{

  private static String  m_boName = "com.ql.party.bo.QParty";



  public final static  String S_Qrticket = "QrTicket";
  public final static  String S_Partyid = "PartyId";
  public final static  String S_Gatheringplace = "GatheringPlace";
  public final static  String S_Imagedata = "ImageData";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Username = "UserName";
  public final static  String S_Creater = "Creater";
  public final static  String S_Cid = "CId";
  public final static  String S_Qrdate = "QrDate";
  public final static  String S_Cname = "CName";
  public final static  String S_Theme = "Theme";
  public final static  String S_State = "State";
  public final static  String S_Endtime = "EndTime";
  public final static  String S_Starttime = "StartTime";
  public final static  String S_Donedate = "DoneDate";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public QPartyBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initQrticket(String value){
     this.initProperty(S_Qrticket,value);
  }
  public  void setQrticket(String value){
     this.set(S_Qrticket,value);
  }
  public  void setQrticketNull(){
     this.set(S_Qrticket,null);
  }

  public String getQrticket(){
       return DataType.getAsString(this.get(S_Qrticket));
  
  }
  public String getQrticketInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Qrticket));
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

  public void initGatheringplace(String value){
     this.initProperty(S_Gatheringplace,value);
  }
  public  void setGatheringplace(String value){
     this.set(S_Gatheringplace,value);
  }
  public  void setGatheringplaceNull(){
     this.set(S_Gatheringplace,null);
  }

  public String getGatheringplace(){
       return DataType.getAsString(this.get(S_Gatheringplace));
  
  }
  public String getGatheringplaceInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Gatheringplace));
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

  public void initQrdate(Timestamp value){
     this.initProperty(S_Qrdate,value);
  }
  public  void setQrdate(Timestamp value){
     this.set(S_Qrdate,value);
  }
  public  void setQrdateNull(){
     this.set(S_Qrdate,null);
  }

  public Timestamp getQrdate(){
        return DataType.getAsDateTime(this.get(S_Qrdate));
  
  }
  public Timestamp getQrdateInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_Qrdate));
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

  public void initTheme(String value){
     this.initProperty(S_Theme,value);
  }
  public  void setTheme(String value){
     this.set(S_Theme,value);
  }
  public  void setThemeNull(){
     this.set(S_Theme,null);
  }

  public String getTheme(){
       return DataType.getAsString(this.get(S_Theme));
  
  }
  public String getThemeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Theme));
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

  public void initEndtime(Timestamp value){
     this.initProperty(S_Endtime,value);
  }
  public  void setEndtime(Timestamp value){
     this.set(S_Endtime,value);
  }
  public  void setEndtimeNull(){
     this.set(S_Endtime,null);
  }

  public Timestamp getEndtime(){
        return DataType.getAsDateTime(this.get(S_Endtime));
  
  }
  public Timestamp getEndtimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_Endtime));
      }

  public void initStarttime(Timestamp value){
     this.initProperty(S_Starttime,value);
  }
  public  void setStarttime(Timestamp value){
     this.set(S_Starttime,value);
  }
  public  void setStarttimeNull(){
     this.set(S_Starttime,null);
  }

  public Timestamp getStarttime(){
        return DataType.getAsDateTime(this.get(S_Starttime));
  
  }
  public Timestamp getStarttimeInitialValue(){
        return DataType.getAsDateTime(this.getOldObj(S_Starttime));
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

