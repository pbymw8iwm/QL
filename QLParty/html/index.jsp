<%@page import="com.ql.wechat.WechatCommons"%>
<%@page import="com.ql.wechat.ReceiveJson"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <title>首页</title>
	
    <meta http-equiv="keywords" content="聚会助手  社交圈">
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    
    <%@ include file="/CommonHead.jsp"%>
  </head>
  
  <body>
    <div class="container">
		<h3>请关注微信公众号：聚会助手</h3>
		<%
		ReceiveJson json = WechatCommons.createQRCode(0, WechatCommons.AccessToken);
		if(json.isError()){
		%>  
		<%=json.getErrMsg() %>
		<%}else{ %>
		<p class="help-block text-center">
		<img src="<%=WechatCommons.Url_ShowQr + json.getTicket()%>" class="img-rounded" width="200" height="200"/>
		</p>
		
		<%}%>
	</div>
  </body>
</html>
