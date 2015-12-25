package com.ql.bo;

import java.sql.*;
import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.ServiceManager;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.DataType;

import com.ql.ivalues.*;

public class CfStaticDataBean extends DataContainer implements DataContainerInterface,ICfStaticDataValue{

  private static String  m_boName = "com.ql.bo.CfStaticData";



  public final static  String S_Codevalue = "CodeValue";
  public final static  String S_State = "State";
  public final static  String S_Sortid = "SortId";
  public final static  String S_Codetype = "CodeType";
  public final static  String S_Codename = "CodeName";
  public final static  String S_Remarks = "Remarks";
  public final static  String S_Extvalue = "ExtValue";

  public static ObjectType S_TYPE = null;
  static{
    try {
      S_TYPE = ServiceManager.getObjectTypeFactory().getInstance(m_boName);
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  public CfStaticDataBean() throws AIException{
      super(S_TYPE);
  }

 public static ObjectType getObjectTypeStatic() throws AIException{
   return S_TYPE;
 }

 public void setObjectType(ObjectType  value) throws AIException{
   //此种数据容器不能重置业务对象类型
   throw new AIException("Cannot reset ObjectType");
 }


  public void initCodevalue(String value){
     this.initProperty(S_Codevalue,value);
  }
  public  void setCodevalue(String value){
     this.set(S_Codevalue,value);
  }
  public  void setCodevalueNull(){
     this.set(S_Codevalue,null);
  }

  public String getCodevalue(){
       return DataType.getAsString(this.get(S_Codevalue));
  
  }
  public String getCodevalueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Codevalue));
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

  public void initSortid(long value){
     this.initProperty(S_Sortid,new Long(value));
  }
  public  void setSortid(long value){
     this.set(S_Sortid,new Long(value));
  }
  public  void setSortidNull(){
     this.set(S_Sortid,null);
  }

  public long getSortid(){
        return DataType.getAsLong(this.get(S_Sortid));
  
  }
  public long getSortidInitialValue(){
        return DataType.getAsLong(this.getOldObj(S_Sortid));
      }

  public void initCodetype(String value){
     this.initProperty(S_Codetype,value);
  }
  public  void setCodetype(String value){
     this.set(S_Codetype,value);
  }
  public  void setCodetypeNull(){
     this.set(S_Codetype,null);
  }

  public String getCodetype(){
       return DataType.getAsString(this.get(S_Codetype));
  
  }
  public String getCodetypeInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Codetype));
      }

  public void initCodename(String value){
     this.initProperty(S_Codename,value);
  }
  public  void setCodename(String value){
     this.set(S_Codename,value);
  }
  public  void setCodenameNull(){
     this.set(S_Codename,null);
  }

  public String getCodename(){
       return DataType.getAsString(this.get(S_Codename));
  
  }
  public String getCodenameInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Codename));
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

  public void initExtvalue(String value){
     this.initProperty(S_Extvalue,value);
  }
  public  void setExtvalue(String value){
     this.set(S_Extvalue,value);
  }
  public  void setExtvalueNull(){
     this.set(S_Extvalue,null);
  }

  public String getExtvalue(){
       return DataType.getAsString(this.get(S_Extvalue));
  
  }
  public String getExtvalueInitialValue(){
        return DataType.getAsString(this.getOldObj(S_Extvalue));
      }


 
 }

