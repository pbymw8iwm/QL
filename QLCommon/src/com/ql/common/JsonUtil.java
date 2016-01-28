package com.ql.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;

import com.ai.appframe2.common.DataContainerInterface;
import com.ai.appframe2.common.DataStructInterface;
import com.ai.appframe2.common.DataType;

public class JsonUtil {
	/**
	 * 将jascript类型的array转换成的json字符串转换为java的List数据结构.如果js数组中某个元素为Array,则对应转换为java.util.List.如果为Object,则对应转换为Map
	 * @param pString
	 * @return
	 * @throws Exception
	 */
	public static List getListFromJsArray(String pString) throws Exception{
 	    List reList = new ArrayList();
		if(StringUtils.isNotBlank(pString)){
			if(!JSONUtils.mayBeJSON(pString)){
				throw new Exception("输入参数字符串不符合JSON规范");
			}
			JSONArray jArray = JSONArray.fromString(pString);
			for (int i = 0; i < jArray.length(); i++) {
				if(JSONUtils.isObject(jArray.get(i))){
					reList.add(getMapFromJsObject(((JSONObject)jArray.get(i)).toString()));
				}
				else{
					reList.add(jArray.get(i));
				}
			}
			
		
			
		}
		return reList;
		
	}
	/**
	 * 将js对象中的Object转换的JSON字符串转换为java的Map对象.如果js中某个对象是Array.则转换为java.util.List;如果是Object.则转换为java.util.Map;
	 * @param pString
	 * @return
	 * @throws Exception
	 */
	public static Map getMapFromJsObject(String pString) throws Exception{
		Map reMap = new HashMap();
		if(StringUtils.isNotBlank(pString)){
			if(!JSONUtils.mayBeJSON(pString)){
				throw new Exception("输入参数字符串不符合JSON规范");
			}
			JSONObject jObject = JSONObject.fromString(pString);
			if(!jObject.isNullObject() && !jObject.isEmpty()){
				for(Iterator i=jObject.keys();i.hasNext();){
					String key = (String)i.next();
					if(JSONUtils.isArray(jObject.get(key))){
						reMap.put(key, JSONArray.toList((JSONArray)jObject.get(key),HashMap.class));
					}
					else if(JSONUtils.isObject(jObject.get(key))){
						reMap.put(key,getMapFromJsObject(((JSONObject)jObject.get(key)).toString()));
					}
					else{
						reMap.put(key, jObject.get(key));
					}
					
				}
			}
			
			
		}
		return reMap;
	}
	
	/**
	 * 将对象数组转换为json字符串
	 * @param pList
	 * @return
	 */
	public static String getJsonFromList(Object[] pArray) throws Exception{
		JSONArray jArray = null;
		if (pArray instanceof DataStructInterface[]) { // 平台容器类型的数组ivalues
			jArray = new JSONArray();
			for (int i = 0; i < pArray.length; i++) {
				DataStructInterface dsi = (DataStructInterface) pArray[i];
				jArray.put(getJsonFromBO(dsi));
			}
		} else 
			jArray = JSONArray.fromArray(pArray);
		return jArray.toString();
	}

	/**
	 * 将maps对象转换为json字符串
	 * @param pMap
	 * @return
	 * @throws Exception
	 */
	public static String getJsonFromMap(Map pMap) throws Exception{
		JSONObject jObject = JSONObject.fromMap(pMap);
		return jObject.toString();
	}
	
	
	
	/**
	 * 将maps对象转换为json字符串
	 * @param pMap
	 * @return
	 * @throws Exception
	 */
	public static String getJsonFromBO(DataStructInterface DSI) throws Exception{
		String[] propertyNames = DSI.getPropertyNames();
		Map map =  new HashMap();
		for(int i=0;i<propertyNames.length;i++){
			Object obj  = DSI.get(propertyNames[i]);
			if (obj instanceof java.util.Date) {
				try {
					obj = "/Date(" + ((java.util.Date) obj).getTime() + "+0800)/";
				} catch (Exception e) {
					throw new Exception("时间类型转换出错!" + e);
				}
			} 
			
			map.put(propertyNames[i], obj);
		}
		return getJsonFromMap(map);
	}
	
	public static void mapToBean(Map map,DataContainerInterface dc)throws Exception{
		String[] keys = (String[])map.keySet().toArray(new String[0]);
		Map keyProperties = dc.getKeyProperties();
		//设置主键
		boolean hasKey = false;
		for(Object key:keyProperties.keySet()){
			String sKey = key.toString();
			for(String k : keys){
				if(sKey.equals(k)){
					Object obj = map.get(k);
					if(obj != null){
					  dc.set(k, obj);
					  hasKey = true;
					}
					break;
				}
			}
		}
		if(hasKey)
			dc.setStsToOld();
		for(int i = 0; i < map.size(); i++) {
			if(keyProperties.containsKey(keys[i]))
				continue;
			if(dc.hasPropertyName(keys[i])){
				Object obj = map.get(keys[i]);
				if(obj == null)
					continue;
				String javaType = dc.getObjectType().getProperty(keys[i]).getJavaDataType();
				if(javaType.equalsIgnoreCase(DataType.DATATYPE_DATE)
						|| javaType.equalsIgnoreCase(DataType.DATATYPE_DATETIME)
						|| javaType.equalsIgnoreCase(DataType.DATATYPE_TIME)){
					String tmp = (String)obj;
					if(tmp.indexOf("(") >= 0){
						String strTime = tmp.substring(tmp.indexOf("(")+1,tmp.indexOf("+"));
						String strZone = tmp.substring(tmp.indexOf("+"),tmp.indexOf(")"));
						TimeZone zone=TimeZone.getTimeZone("GMT"+strZone);  
						Calendar c=Calendar.getInstance(zone);  
						c.setTimeInMillis(DataType.getAsLong(strTime)); 
						dc.set(keys[i], c.getTime());
					}
					else if(tmp.indexOf("T") > 0){
						dc.set(keys[i], tmp.replaceFirst("T", " "));
					}
					else
						dc.set(keys[i], obj);
				}
				else{
					dc.set(keys[i], obj);
				}
			}
			else{
//				System.out.println(keys[i]);
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			HashMap m= new HashMap();
			HashMap m1=new HashMap();
			HashMap m3=new HashMap();
			m.put("m1", "1");
			m.put("m2", new Integer(2));
			m.put("m4", new Long(1));
			m.put("m3", m1);
			List l = new ArrayList();
			l.add("aaa");
			l.add(m3);
			m3.put("m4444", "ss");
			m1.put("m33", l);
			System.out.println(JsonUtil.getJsonFromMap(m)); 
//			//String tesStr = "[\"abc\",\"cde\",\"efg\",[\"cc\",\"ff\",\"kk\"],22,33]";
//			String tesStr = "[\"abc\",\"cde\",\"efg\",{\"a\":[\"a2_1\"],\"b\":{\"w\":\"ww\",\"c\":\"cc\"},\"c\":\"cccc\"},22,33]";
//			String tesStr2 = "{\"a\":[\"a1_1\",\"a1_2\"],\"b\":[\"a2_1\",[\"a1_1\",\"a1_2\"]],\"c\":{\"w\":\"ww\",\"c\":\"cc\"}}";
//			System.out.println(JSONUtils.mayBeJSON(tesStr));
//			List s = JsonUtil.getListFromJsArray(tesStr);
//			for (int i = 0; i < s.size(); i++) {
//				System.out.println("valueClass="+(s.get(i)).getClass()+",value="+s.get(i));
//			}
//			Map m = JsonUtil.getMapFromJsObject(tesStr2);
//			for(Iterator i=m.keySet().iterator();i.hasNext();){
//				String key = (String)i.next();
//				System.out.println("key="+key+",valueClass="+m.get(key).getClass()+",value="+m.get(key));
//			}
			
//			List l = new ArrayList();
//			l.add("aaa");
//			l.add(new Long(1));
//			l.add(new Integer(2));
//			HashMap m= new HashMap();
//			m.put("m1", "1");
//			m.put("m2", new Integer(2));
//			Map mm = new HashMap();
//			List ll = new ArrayList();
//			mm.put("mm1", "m");
//			mm.put("mm2", ll);
//			m.put("m3", mm);
//			//m.put("m4", new ArrayList().add("bb"));
//			l.add(m);
//			System.out.println(JsonUtil.getJsonFromList(l.toArray()));
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
