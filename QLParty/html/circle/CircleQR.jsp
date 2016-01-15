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
	cType = sc.getExtAttr("TypeName")+"圈";
}
else{
	cName = HttpUtil.getAsString(request, "cName");
	cImg = URLDecoder.decode(HttpUtil.getAsString(request, "cImg"),"UTF-8"); 
	ticket = HttpUtil.getAsString(request, "ticket");
	cType = "";
}
%> 
  <head>
    <title>邀您加入圈子</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  <body>
    <div class="container">
        <br/>
		<div class="panel panel-success">
		  <div class="panel-heading">圈名片</div>
		  <div class="panel-body">
		    <p class="help-block text-center">
				<img src="<%=cImg %>" class="img-rounded" width="80" height="80"/>
				<br/>
				<strong><%=cName %></strong>
				<br/>
				<%=cType %>
				<br/>
				<img src="<%=WechatCommons.Url_ShowQr + ticket%>" class="img-rounded" width="200" height="200"/>
				<br/>
				扫码加入此圈，参与聚会，分享照片
			</p>
		  </div>
		</div>
	</div>
  </body>
</html>
