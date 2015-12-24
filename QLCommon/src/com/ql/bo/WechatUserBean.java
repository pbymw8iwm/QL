package com.ql.bo;

import com.ai.appframe2.bo.DataContainer;
import com.ai.appframe2.common.AIException;
import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataType;
import com.ai.appframe2.common.ObjectType;
import com.ai.appframe2.common.ServiceManager;
import com.ql.ivalues.IWechatUserValue;

public class WechatUserBean extends DataContainer implements DataContainerInterface,IWechatUserValue{

  private static String  m_boName = "com.xerrys.rehab.bo.WechatUser";



  public final static  String S_Usertype = "UserType";
  public final static  String S_State = "State";
  public final static  String S_Userid = "UserId";
  public final static  String S_Openid = "OpenId";

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
   //�������������������ҵ���������
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


 
 }

