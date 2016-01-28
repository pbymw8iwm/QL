<%@page import="com.ql.party.web.PartyAction"%>
<%@page import="com.ql.party.ivalues.ISocialCircleValue"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<%
Long cId = HttpUtil.getAsLong(request, "cId");
String cName;
String cImg;
String cType;
String ticket;
String expiryDate;
if(cId > 0){
	ISocialCircleValue sc = PartyAction.getSocialCircle(cId,false);
	if(sc == null){
%>
<body><h3>圈子可能已经被删除</h3></body>
<%
	  return;
	}
	cName = sc.getCname();
	cImg = sc.getImagedata();
	ticket = sc.getQrticket();
	cType = sc.getExtAttr("TypeName").toString();
	expiryDate = sc.getExtAttr("QRExpiryDate").toString();
}
else{
	cName = HttpUtil.getAsString(request, "cName");
	cImg = URLDecoder.decode(HttpUtil.getAsString(request, "cImg"),"UTF-8"); 
	ticket = HttpUtil.getAsString(request, "ticket");
	cType = "";
	expiryDate = "";
}
%> 
  <head>
    <title>邀请您加入圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  <body>
<div style="background-color:#4A4A4A;width:100%;height:100%;padding-top:50px">
  <div style="border-radius: 6px;background-color:#ffffff;width:80%;margin-right:auto;margin-left:auto;padding-top:10px;padding-bottom:10px">
    <table style="margin-top:10px;margin-left:10px">
      <tr>
        <td rowspan="2"><img src="<%=cImg %>" style="width:60px;height:60px;margin-right:10px;"></td>
        <td style="padding-top:10px"><strong><%=cName %></strong></td>
      </tr>
      <tr>
        <td><span class="help-block" ><%=cType %></span></td>
      </tr>
    </table>
    <p class="text-center">
		<img src="<%=WechatCommons.Url_ShowQr + ticket%>" style="width:220px;height:220px;padding-top:10px"/>
		<br/>
		扫码加入此圈，参与聚会，分享照片
		<br/>
		<span class="help-block" style="font-size:10px;">当前二维码将于<%=expiryDate %>失效</span>
	</p>
  </div>
</div>
  </body>
</html>
