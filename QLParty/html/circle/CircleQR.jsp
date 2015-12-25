<%@page import="com.ai.appframe2.web.HttpUtil"%>
<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.wechat.ReceiveJson"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>圈名片</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
<%
String userName = HttpUtil.getAsString(request, "userName");
String cName = HttpUtil.getAsString(request, "cName");
String cId = HttpUtil.getAsString(request, "cId");
%> 
  <body>
    <div class="container">
		<h3><%=userName %>分享的圈子：</h3>
		<%
		ReceiveJson json = WechatCommons.createQRCode("c_"+cId, WechatCommons.AccessToken);
		if(json.isError()){
		%>  
		<%=json.getErrMsg() %>
		<%}else{ %>
		<p class="help-block text-center">
		<%=cName %>
		<br/>
		<img src="<%=WechatCommons.Url_ShowQr + json.getTicket()%>" class="img-rounded" width="200" height="200"/>
		<br/>
		扫码加入此圈，参与聚会，分享照片
		</p>
		<%}%>
	</div>
  </body>
</html>
